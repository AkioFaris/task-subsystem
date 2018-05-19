package moocplatform.task.services;

import moocplatform.task.pojos.ProblemRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static moocplatform.task.enums.DbConfigurations.*;

/**
 * Database manager is a Singleton which performs a work with the database according to needs of task sub-system
 */
public class DbManager {
    private static DbManager instance;
    private Connection connection;

    private final static Logger logger = LoggerFactory.getLogger(DbManager.class);
    private final String USE_DB_QUERY = "USE " + DB_NAME.text;

    private DbManager() throws SQLException {
        try {
            logger.info("Trying to set up a connection to database: " + DB_NAME.text);
            Class.forName(DRIVER.text);
            this.connection = DriverManager.getConnection(JDBC_URL.text, USER_NAME.text, PASS.text);
            logger.info("Set up connection with database {} by user {} with password {}", DB_NAME.text, USER_NAME.text,
                    PASS.text);
        } catch (ClassNotFoundException ex) {
            logger.error("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DbManager getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DbManager();
        }
        logger.info("Got DB instance");
        return instance;
    }

    /**
     * Adds a new problemRequest to the data base
     * @param problemRequest ProblemRequest - a problemRequest to add
     */
    public void addProblem(ProblemRequest problemRequest) throws SQLException {
        logger.info("Input values: problemRequest {}\n", problemRequest);
        String addProblem = "INSERT INTO problems(statement, difficulty, topic_id, discipline_id, " +
                "start_expression, final_expression) VALUES('" + problemRequest.statement + "', "
                + problemRequest.difficulty + "," + problemRequest.topicId + ", " + problemRequest.disciplineId + ", '"
                + problemRequest.startExpression + "', '" + problemRequest.finalExpression + "')";

        Statement statement = connection.createStatement();
        statement.executeQuery(USE_DB_QUERY);
        statement.executeUpdate(addProblem);
        logger.info("Added a problem successfully");
    }

    /**
     * Gets an array of problems formulations by test id
     * @param testId long - id of a test
     * @return String[] - a list of problems formulations
     */
    public String[] getProblemsFormulations(long testId) throws SQLException {
        logger.info("Input values: testId " + testId);
        String selectProblemsByTestId = "SELECT pr.statement FROM problems pr INNER JOIN tests_problems t_pr " +
                "ON pr.problem_id = t_pr.problem_id WHERE t_pr.test_id = " + testId;

        Statement statement = connection.createStatement();
        statement.executeQuery(USE_DB_QUERY);
        ResultSet resultSet = statement.executeQuery(selectProblemsByTestId);
        ArrayList<String> formulations = new ArrayList<>();
        while (resultSet.next()) {
            formulations.add(resultSet.getString(1));
        }
        logger.info("Got problems formulations " + formulations + "for the test with id " + testId);
        return formulations.toArray(new String[formulations.size()]);
    }

    /**
     * Gets an array of problems ids in the database
     * @param disciplineId long - if of a discipline
     * @param topicIds long[] - topics ids
     * @param problemsDifficulties int[] - problems difficulties
     * @return long[] - an array of problems ids
     */
    public long[] getProblemsIds(long disciplineId, long[] topicIds, int[] problemsDifficulties, int[] amountByDifficulties)
            throws SQLException {
        logger.info("Input values: disciplineId {}\n topicIds {}\n problemsDifficulties {}\n", disciplineId, topicIds,
                problemsDifficulties);
        Statement statement = connection.createStatement();
        statement.executeQuery(USE_DB_QUERY);

        ArrayList<Long> problemIds = new ArrayList<>();
        StringBuilder defaultSelect = new StringBuilder("SELECT problem_id FROM problems WHERE discipline_id = ")
                .append(disciplineId).append(" AND (topic_id = ").append(topicIds[0]);
        for (long topicId : topicIds) {
            defaultSelect.append(" OR topic_id = ").append(topicId);
        }
        for (int i = 0; i < problemsDifficulties.length; i++) {
            StringBuilder selectProblems = new StringBuilder(defaultSelect);
            selectProblems.append(") AND difficulty = ").append(problemsDifficulties[i]);
            logger.info("SQL query:" + selectProblems + "\n");

            ResultSet resultProblems = statement.executeQuery(selectProblems.toString());
            int j = 0;
            while (resultProblems.next() && j < amountByDifficulties[i]) {
                problemIds.add(resultProblems.getLong(1));
                ++j;
            }
        }
        logger.info("Got problems ids " + problemIds + " for the discipline with id " + disciplineId + "\n");
        return problemIds.stream().mapToLong(l -> l).toArray();
    }

    /**
     * Initializes a test with problems by their ids and returns the new test's id
     * @param problemIds long[] - problems ids
     * @return long - the new test's id
     */
    public long initTest(long disciplineId, long[] problemIds) throws SQLException {
        logger.info("Input values: problemIds {}\n scores {}\n", problemIds);
        Statement statement = connection.createStatement();
        statement.executeQuery(USE_DB_QUERY);

        String initTest = "INSERT INTO tests(discipline_id) VALUES('" + disciplineId + "')";
        statement.executeUpdate(initTest);

        String selectTest = "SELECT LAST_INSERT_ID() AS LAST_ID";
        ResultSet resultTest = statement.executeQuery(selectTest);
        resultTest.next();
        String testId = resultTest.getString("LAST_ID");

        for (int i = 0; i < problemIds.length; ++i) {
            String addTestProblem = "INSERT INTO tests_problems(test_id, problem_id, score, problem_local_id) " +
                "VALUES(" + testId + ", " + problemIds[i] + ", " + 0 + ", " + i + ")";
            logger.info("SQL query:" + addTestProblem + "\n");
            statement.executeUpdate(addTestProblem);
        }
        logger.info("Added a new test draft successfully");
        return 0;
    }

    /**
     * Finishes test creation by adding scores to the existing problems in test
     * @param testId long - test id
     * @param scores int[] - scores of the test problems
     */
    public void createTest(long testId, int[] scores) throws SQLException {
        logger.info("Input values: testId " + testId + " scores " + scores);
        Statement statement = connection.createStatement();
        statement.executeQuery(USE_DB_QUERY);
        for (int i = 0; i < scores.length; ++i) {
            String addScore = "UPDATE tests_problems SET score = " + scores[i] + " WHERE test_id = " + testId
                    + " AND problem_local_id = " + i;
            statement.executeUpdate(addScore);
        }
        logger.info("Added scores successfully");
    }

    /**
     * Gets problem's start and final expressions
     * @param problemId long problem
     * @return Map<String,String> with "startExpression" and "finalExpression"
     */
    public Map<String,String> getStartFinalExpressions(long problemId) throws SQLException {
        Map<String,String> expressions = new HashMap<>();
        String selectExpressions = "SELECT pr.start_expression, pr.final_expression FROM problems pr " +
                "WHERE pr.problem_id = " + problemId;

        Statement statement = connection.createStatement();
        statement.executeQuery(USE_DB_QUERY);
        ResultSet resultSet = statement.executeQuery(selectExpressions);
        resultSet.next();
        expressions.put("startExpression", resultSet.getString(1));
        expressions.put("finalExpression", resultSet.getString(2));

        logger.info("Got start and final expressions " + expressions + "for the problem with id " + problemId);
        return expressions;
    }

    /**
     * Gets problem's data
     * @param testId long test id
     * @param localId long local (in test) problem id
     * @return Map<String,String> problem's data (global id and score in the test)
     */
    public Map<String,String> getProblemData(long testId, long localId) throws SQLException {
        Map<String,String> data = new HashMap<>();
        logger.info("Input values: testId " + testId + ", localId " + localId);
        String selectProblemsIdsByTestId = "SELECT pr.problem_id, t_pr.score FROM problems pr " +
                "INNER JOIN tests_problems t_pr ON pr.problem_id = t_pr.problem_id WHERE t_pr.test_id = " + testId
                + " AND t_pr.problem_local_id = " + localId;

        Statement statement = connection.createStatement();
        statement.executeQuery(USE_DB_QUERY);
        ResultSet resultSet = statement.executeQuery(selectProblemsIdsByTestId);
        resultSet.next();
        data.put("problemId", String.valueOf(resultSet.getLong(1)));
        data.put("score", String.valueOf(resultSet.getInt(2)));
        logger.info("Got data " + data + " for the " + localId + " problem of the test with id " + testId);
        return data;
    }

    /**
     * Gets problems scores sum
     * @param testId long test id
     * @return it sum of test's problems scores
     * @throws SQLException
     */
    public int getScoresSum(long testId) throws SQLException {
        logger.info("Input values: testId " + testId);
        String selectProblemsByTestId = "SELECT t_pr.score FROM tests_problems t_pr WHERE t_pr.test_id = " + testId;

        Statement statement = connection.createStatement();
        statement.executeQuery(USE_DB_QUERY);
        ResultSet resultSet = statement.executeQuery(selectProblemsByTestId);
        int scoresSum = 0;
        while (resultSet.next()) {
            scoresSum += resultSet.getInt(1);
        }
        logger.info("Got problems scores sum " + scoresSum + " for the test with id " + testId);
        return scoresSum;
    }
}
