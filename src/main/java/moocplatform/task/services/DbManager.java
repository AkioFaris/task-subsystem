package moocplatform.task.services;

import moocplatform.task.pojos.ProblemRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database manager is a Singleton which performs a work with the database according to needs of task sub-system
 */
public class DbManager {
    private static DbManager instance;
    private Connection connection;

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "apprz_db";
    private static final String DB_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
    private static final String DB_USER_NAME = "FAN";
    private static final String DB_PASS = "2018";

    private final static Logger logger = LoggerFactory.getLogger(DbManager.class);


    private DbManager() throws SQLException {
        try {
            Class.forName(DB_DRIVER);
            this.connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
            logger.info("Set up connection with database {} by user {} with password {}", DB_NAME, DB_USER_NAME, DB_PASS);
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
    public void addProblem(ProblemRequest problemRequest) {
        logger.info("Input values: problemRequest {}\n", problemRequest);
        // implementation omitted
    }

    /**
     * Gets an array of problems formulations by test id
     * @param testId long - id of a test
     * @return String[] - a list of problems formulations
     */
    public String[] getProblemsFormulations(long testId) {
        logger.info("Input values: testId {}\n", testId);
        // implementation omitted
        return null;
    }

    /**
     * Gets an array of problems ids in the database
     * @param disciplineId long - if of a discipline
     * @param topicIds long[] - topics ids
     * @param problemsDifficulties int[] - problems difficulties
     * @return long[] - an array of problems ids
     */
    public long[] getProblemsIds(long disciplineId, long[] topicIds, int[] problemsDifficulties) {
        logger.info("Input values: disciplineId {}\n topicIds {}\n problemsDifficulties {}\n", disciplineId, topicIds,
                problemsDifficulties);
        // implementation omitted
        return null;
    }

    /**
     * Initializes a test with problems by their ids and returns the new test's id
     * @param problemIds long[] - problems ids
     * @return long - the new test's id
     */
    public long initTest(long[] problemIds) {
        logger.info("Input values: problemIds {}\n scores {}\n", problemIds);
        // implementation omitted
        return 0;
    }

    /**
     * Finishes test creation by adding scores to the existing problems in test
     * @param testId long - test id
     * @param scores int[] - scores of the test problems
     */
    public void createTest(long testId, int[] scores) {
        logger.info("Input values: testId {}\n scores {}\n", testId, scores);
        // implementation omitted
    }
}
