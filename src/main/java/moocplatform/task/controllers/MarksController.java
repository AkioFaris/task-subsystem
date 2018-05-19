package moocplatform.task.controllers;

import moocplatform.task.pojos.TestSolutionRequest;
import moocplatform.task.services.TestHandler;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
     * @param testSolutionRequest String - test solution to evaluate
     * @return int - a mark
     * @throws SQLException
     */
    @RequestMapping(value = "/{testId}", method = RequestMethod.GET)
    public int evaluateTestSolution(@PathVariable long testId, @RequestBody TestSolutionRequest testSolutionRequest)
            throws SQLException, IOException {
        return TestHandler.evaluate(testId, testSolutionRequest.testSolution);
    }
}
