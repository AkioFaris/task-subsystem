package moocplatform.task.services;

import java.sql.SQLException;

/**
 * TODO: fill JavaDoc
 */
public class TestHandler {
    /**
     * Evaluates test solution
     * @param testId long - test id
     * @param testSolution String - testSolution
     * @return int - a score
     * @throws SQLException
     */
    public static int evaluate(long testId, String testSolution) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        //TODO: get problems start and final expressions from db with assistance of dbManager
        //TODO: use Katsman's API to check solutions
        // implementation omitted
        return 0;
    }
}
