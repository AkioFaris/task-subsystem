package moocplatform.task.controllers;

import moocplatform.task.pojos.ProblemsSet;
import moocplatform.task.pojos.ProblemsSetRequest;
import moocplatform.task.services.DbManager;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * Test controller handles requests on managing test
 */
@RestController
@RequestMapping(value = "/task/tests", consumes = "application/json", produces = "application/json")
public class TestsController {

    /**
     * Generates a problems set
     * @param problemsSetRequest ProblemsSetRequest -
     * @return ProblemsSet - problems descriptions and test id
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.POST)
    public ProblemsSet generateProblemsSet(@RequestBody ProblemsSetRequest problemsSetRequest) throws SQLException {
        return new ProblemsSet(problemsSetRequest);
    }

    /**
     * Creates a new test by problems set id (the new test id) and scores for each problem of the set
     * @param testId long - test id, which was obtained after problems set generation
     * @param scores int[] - scores for each problem of the problems set
     * @throws SQLException
     */
    @RequestMapping(value = "/{testId}", method = RequestMethod.PUT)
    public void createTest(@PathVariable long testId, @RequestBody int[] scores) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        dbManager.createTest(testId, scores);
    }
}
