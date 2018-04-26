package moocplatform.task.pojos;

/**
 * A POJO of a problems set's request
 */
public class ProblemsSetRequest {
    public final long disciplineId;
    public final long[] topicIds;
    public final int[] problemsDifficulties;

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
}
