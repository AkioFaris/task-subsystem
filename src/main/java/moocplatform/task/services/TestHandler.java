package moocplatform.task.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.sql.SQLException;

import java.util.Map;

import static moocplatform.task.utils.DataFormatter.unifyExpression;
import static org.springframework.http.HttpHeaders.USER_AGENT;

/**
 * Test handler allows to evaluate test solutions and mark them
 */
public class TestHandler {
    private final static Logger logger = LoggerFactory.getLogger(TestHandler.class);
    private final static String CHECKER_URL =
            "http://vikto9494.wallst.ru/cgi-bin/rest_cgi_api.cgi?check_without_substitutions?";

    /**
     * Checks if all problem's solution calculations are equivalent
     * @param expressions String[]
     * @return boolean
     */
    private static boolean areAllCalculationsEquivalent(String[] expressions) throws IOException {
        for(int i = 0; i < expressions.length - 1; ++i) {
            if (!areCalculationsEquivalent(expressions[i], expressions[i + 1])) {
                logger.info("Found a mistake: " + expressions[i] + " !=  " + expressions[i + 1]);
                return false;
            }
        }
        logger.info("Checked that all problem's solution calculations are equivalent");
        return true;
    }

    /**
     * Checks if the two given solution calculations are equivalent
     * @param expression1 String
     * @param expression2 String
     * @return boolean
     * @throws IOException
     */
    private static boolean areCalculationsEquivalent(String expression1, String expression2)
            throws IOException {
        String urlStr = CHECKER_URL + expression1 + "?" + expression2;
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        logger.info("Sending 'GET' request to URL : " + urlStr);
        logger.info("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        logger.info("Response text: " + response);
        return response.toString().contains("Tend");
    }

    /**
     * Normalizes given result score
     * @param resultScore double
     * @return normalized score 2/3/4/5
     */
    private static int normalizeResultScore(double resultScore) {
        double percentage = resultScore * 100;
        int score = 2;
        if (percentage >= 90) {
            score = 5;
        } else if (percentage >= 70) {
            score = 4;
        } else if (percentage >= 50) {
            score = 3;
        }
        logger.info("The result score " + resultScore + " was normalized to " + score);
        return score;
    }

    /**
     * Evaluates test solution
     * @param testId long - test id
     * @param testSolution String - testSolution
     * @return int - a score
     * @throws SQLException
     */
    public static int evaluate(long testId, String testSolution) throws SQLException, IOException {
        logger.info("Input values: testId" + testId + "testSolution " + testSolution);
        DbManager dbManager = DbManager.getInstance();
        Map<String, String> startFinalExpr;
        int scoresSum = dbManager.getScoresSum(testId);
        double resultScore = 0;

        String[] solutions = testSolution.split("Problem ");
        for (int i = 1; i < solutions.length; i++) {
            String solution = solutions[i];
            solution = unifyExpression(solution);
            String[] solutionParts = solution.replace("№", "").split("Solution:");
            long problemLocalId = Long.valueOf(solutionParts[0]);

            Map<String, String> testProblemData = dbManager.getProblemData(testId, problemLocalId);
            long problemId = Long.valueOf(testProblemData.get("problemId"));
            int score = Integer.valueOf(testProblemData.get("score"));
            double normScore = (double) score / scoresSum;

            startFinalExpr = dbManager.getStartFinalExpressions(problemId);
            String[] expressions = solutionParts[1].split("=");
            String expectedStartExpr = unifyExpression(startFinalExpr.get("startExpression"));
            String expectedFinalExpr = unifyExpression(startFinalExpr.get("finalExpression"));
            if (expectedStartExpr.equals(expressions[0])
                    && expectedFinalExpr.equals(expressions[expressions.length - 1])
                    && areAllCalculationsEquivalent(expressions)) {
                resultScore += normScore;
            }
        }

        return normalizeResultScore(resultScore);
    }
}
