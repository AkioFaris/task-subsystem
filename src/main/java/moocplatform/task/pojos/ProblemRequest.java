package moocplatform.task.pojos;

/**
 * A POJO of a problem
 */
public class ProblemRequest {
    public final long disciplineId;
    public final long topicId;
    public final int difficulty;
    public final String statement;
    public final String startExpression;
    public final String finalExpression;

    /**
     * A problem's constructor
     * @param disciplineId long - id of a discipline
     * @param topicId long - id of a topic
     * @param difficulty int - difficulty
     * @param statement String - statement of the problem
     * @param startExpression String - statement's start expression
     * @param finalExpression String - statement's final expression
     */
    public ProblemRequest(long disciplineId, long topicId, int difficulty, String statement, String startExpression,
                          String finalExpression) {
        this.disciplineId = disciplineId;
        this.topicId = topicId;
        this.difficulty = difficulty;
        this.statement = statement;
        this.startExpression = startExpression;
        this.finalExpression = finalExpression;
    }

    /** Returns String representation of problem's data
     * @return String
     */
    @Override
    public String toString() {
        return "ProblemRequest{" +
                "disciplineId=" + disciplineId +
                ", topicId=" + topicId +
                ", difficulty=" + difficulty +
                ", statement='" + statement + '\'' +
                ", startExpression='" + startExpression + '\'' +
                ", finalExpression='" + finalExpression + '\'' +
                '}';
    }
}
