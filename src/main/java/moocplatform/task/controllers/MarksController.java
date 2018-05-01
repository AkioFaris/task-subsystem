package moocplatform.task.controllers;

import moocplatform.task.services.TestHandler;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * Mark controller handles requests on test solutions evaluation
 */
@RestController
@RequestMapping(value = "/task/marks", consumes = "application/json", produces = "application/json")
public class MarksController {

    /**
     * Marks test solution
     * @param testId long - test id
     * @param testSolution String - test solution to evaluate
     * @return int - a mark
     * @throws SQLException
     */
    @RequestMapping(value = "/{testId}", method = RequestMethod.GET)
    public int evaluateTestSolution(@PathVariable long testId, @RequestBody String testSolution) throws SQLException {
        return TestHandler.evaluate(testId, testSolution);
    }
}
