package moocplatform.task.pojos;

import java.io.Serializable;

/**
 * A POJO of a test solution
 */
public class TestSolutionRequest implements Serializable {
    public String testSolution;

    public TestSolutionRequest() {
    }

    public TestSolutionRequest(String testSolution) {
        this.testSolution = testSolution;
    }

    public void setTestSolution(String testSolution) {
        this.testSolution = testSolution;
    }
}
