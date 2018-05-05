package moocplatform.task.pojos;

import moocplatform.task.services.DbManager;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * A POJO of a problems set
 */
public class ProblemsSet implements Serializable {
    public String[] problemsDescriptions;
    public long testId;

    public ProblemsSet() {
    }

    /**
     * Problems Set constructor
     * @param problemsDescriptions String[] - problems descriptions
     * @param testId long - test id
     */
    public ProblemsSet(String[] problemsDescriptions, long testId) {
        this.problemsDescriptions = problemsDescriptions;
        this.testId = testId;
    }

    /**
     * Problems Set constructor
     * @param problemsSetRequest ProblemsSetRequest - request body with problems descriptions and test id
     * @throws SQLException
     */
    public ProblemsSet(ProblemsSetRequest problemsSetRequest) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        final long[] problemIds = dbManager.getProblemsIds(problemsSetRequest.disciplineId, problemsSetRequest.topicIds,
                problemsSetRequest.problemsDifficulties);
        testId = dbManager.initTest(problemIds);
        problemsDescriptions = dbManager.getProblemsFormulations(testId);
    }

    public void setProblemsDescriptions(String[] problemsDescriptions) {
        this.problemsDescriptions = problemsDescriptions;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }
}
