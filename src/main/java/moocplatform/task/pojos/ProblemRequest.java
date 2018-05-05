package moocplatform.task.pojos;

import java.io.Serializable;

/**
 * A POJO of a problem
 */
public class ProblemRequest implements Serializable {
    public long disciplineId;
    public long topicId;
    public int difficulty;
    public String statement;
    public String startExpression;
    public String finalExpression;

    public ProblemRequest() {
    }

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

    public void setDisciplineId(long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setStartExpression(String startExpression) {
        this.startExpression = startExpression;
    }

    public void setFinalExpression(String finalExpression) {
        this.finalExpression = finalExpression;
    }
}
