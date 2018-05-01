package moocplatform.task.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Test handler allows to evaluate test solutions and mark them
 */
public class TestHandler {
    private final static Logger logger = LoggerFactory.getLogger(TestHandler.class);

    /**
     * Evaluates test solution
     * @param testId long - test id
     * @param testSolution String - testSolution
     * @return int - a score
     * @throws SQLException
     */
    public static int evaluate(long testId, String testSolution) throws SQLException {
        logger.info("Input values: testId {}\n testSolution {}\n", testId, testSolution);

        DbManager dbManager = DbManager.getInstance();
        //TODO: get problems start and final expressions from db with assistance of dbManager
        //TODO: use Katsman's API to check solutions
        // implementation omitted
        return 0;
    }
}
