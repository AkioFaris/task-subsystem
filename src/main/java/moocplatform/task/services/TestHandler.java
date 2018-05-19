package moocplatform.task.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import java.util.Map;

import static moocplatform.task.utils.DataFormatter.unifyExpression;

/**
 * Test handler allows to evaluate test solutions and mark them
 */
public class TestHandler {
    private final static Logger logger = LoggerFactory.getLogger(TestHandler.class);

    /**
     * Normalizes given result score by scoresSum
     * @param scoresSum int
     * @param resultScore double
     * @return normalized score 2/3/4/5
     */
    public static int normalizeResultScore(int scoresSum, double resultScore) {
        double percentage = resultScore / scoresSum * 100;
        int score = 2;
        if (percentage >= 90) {
            score = 5;
        } else if (percentage >= 70) {
            score = 4;
        } else if (percentage >= 50) {
            score = 3;
        }
        logger.info("The result score " + resultScore + " out of the maximum " + scoresSum
                + " was normalized to " + score);
        return score;
    }

    /**
     * Evaluates test solution
     * @param testId long - test id
     * @param testSolution String - testSolution
     * @return int - a score
     * @throws SQLException
     */
    public static int evaluate(long testId, String testSolution) throws SQLException {
        logger.info("Input values: testId" + testId + "testSolution " + testSolution);
        DbManager dbManager = DbManager.getInstance();
        Map<String, String> startFinalExpr;
        int scoresSum = dbManager.getScoresSum(testId);
        double resultScore = 0;

        String[] solutions = testSolution.split("Problem ");
        for (String solution: solutions) {
            solution = unifyExpression(solution);
            String[] solutionParts = solution.replace("№", "").split("Solution:");
            long problemLocalId = Long.valueOf(solutionParts[0]);

            Map<String, String> testProblemData = dbManager.getProblemData(testId, problemLocalId);
            long problemId = Long.valueOf(testProblemData.get("problemId"));
            int score = Integer.valueOf(testProblemData.get("score"));
            double normScore = score / scoresSum;

            startFinalExpr = dbManager.getStartFinalExpressions(problemId);
            String[] expressions = solutionParts[1].split("=");
            String expectedStartExpr = unifyExpression(startFinalExpr.get("startExpression"));
            String expectedFinalExpr = unifyExpression(startFinalExpr.get("finalExpression"));
            if (expectedStartExpr.equals(expressions[0]) && expectedFinalExpr.equals(expressions[1])) {
                resultScore += normScore;
            }
        }

        return normalizeResultScore(scoresSum, resultScore);
    }
}
