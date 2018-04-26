package moocplatform.task.controllers;

import moocplatform.task.pojos.ProblemsSet;
import moocplatform.task.pojos.ProblemsSetRequest;
import moocplatform.task.services.DbManager;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * TODO: fill JavaDoc
 */
@RestController
@RequestMapping(value = "/task/tests", consumes = "application/json", produces = "application/json")
public class TestsController {

    /**
     * TODO: fill JavaDoc
     * @param problemsSetRequest
     * @return
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.POST)
    public ProblemsSet generateProblemsSet(@RequestBody ProblemsSetRequest problemsSetRequest) throws SQLException {
        return new ProblemsSet(problemsSetRequest);
    }

    /**
     * TODO: fill JavaDoc
     * @param testId
     * @param scores
     * @throws SQLException
     */
    @RequestMapping(value = "/{testId}", method = RequestMethod.PUT)
    public void createTest(@PathVariable long testId, @RequestBody int[] scores) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        dbManager.createTest(testId, scores);
    }
}
