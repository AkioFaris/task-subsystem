package moocplatform.task.controllers;

import moocplatform.task.pojos.ProblemRequest;
import moocplatform.task.services.DbManager;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * Problems controller handles requests on managing problems
 */
@RestController
@RequestMapping(value = "/task/problems", consumes = "application/json", produces = "application/json")
public class ProblemsController {

    /**
     * Gets problems formulations
     * @param testId long - test id
     * @return String[] - an array with problems formulations
     * @throws SQLException
     */
    @RequestMapping(value = "/{testId}", method = RequestMethod.GET)
    public String[] getProblemsFormulations(@PathVariable long testId) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        return dbManager.getProblemsFormulations(testId);
    }

    /**
     * Adds a problem to the database
     * @param problemRequest ProblemRequest - a set of problems data (discipline and topic ids, difficulty, statement,
     *                      start and final expressions)
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.POST)
    public void addProblem(@RequestBody ProblemRequest problemRequest) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        dbManager.addProblem(problemRequest);
    }
}
