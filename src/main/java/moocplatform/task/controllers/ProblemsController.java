package moocplatform.task.controllers;

import moocplatform.task.pojos.ProblemRequest;
import moocplatform.task.services.DbManager;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * TODO: fill JavaDoc
 */
@RestController
@RequestMapping(value = "/task/problems", consumes = "application/json", produces = "application/json")
public class ProblemsController {

    /**
     * TODO: fill JavaDoc
     * @param testId
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/{testId}", method = RequestMethod.GET)
    public String[] getProblemsFormulations(@PathVariable long testId) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        return dbManager.getProblemsFormulations(testId);
    }

    /**
     * TODO: fill JavaDoc
     * @param problemRequest
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.POST)
    public void addProblem(@RequestBody ProblemRequest problemRequest) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        dbManager.addProblem(problemRequest);
    }
}
