package moocplatform.task.pojos;

import java.io.Serializable;

/**
 * A POJO of a problems set's request
 */
public class ProblemsSetRequest implements Serializable {
    public long disciplineId;
    public long[] topicIds;
    public int[] problemsDifficulties;

    public ProblemsSetRequest() {
    }

    /**
     * A problem's constructor
     * @param disciplineId long - id of a discipline
     * @param topicIds long[] - ids of a topics
     * @param problemsDifficulties int[] - problems difficulties
     */
    public ProblemsSetRequest(long disciplineId, long[] topicIds, int[] problemsDifficulties) {
        this.disciplineId = disciplineId;
        this.topicIds = topicIds;
        this.problemsDifficulties = problemsDifficulties;
    }

    public void setDisciplineId(long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public void setTopicIds(long[] topicIds) {
        this.topicIds = topicIds;
    }

    public void setProblemsDifficulties(int[] problemsDifficulties) {
        this.problemsDifficulties = problemsDifficulties;
    }
}
