package moocplatform.task.controllers;

import moocplatform.task.services.TestHandler;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * TODO: fill JavaDoc
 */
@RestController
@RequestMapping(value = "/task/marks", consumes = "application/json", produces = "application/json")
public class MarksController {

    /**
     * TODO: fill JavaDoc
     * @param testId
     * @param testSolution
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/{testId}", method = RequestMethod.GET)
    public int evaluateTestSolution(@PathVariable long testId, @RequestBody String testSolution) throws SQLException {
        return TestHandler.evaluate(testId, testSolution);
    }
}
