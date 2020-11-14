package ru.progwards.tasktracker.service.vo;

/**
 * value object - объект бизнес логики (связанная задача)
 *
 * @author Oleg Kiselev
 */
public class RelatedTask {

    private final Long id;
    private RelationType relationType;
    private Long parentTaskId;
    private Long taskId;

    public RelatedTask(Long id, RelationType relationType, Long parentTaskId, Long taskId) {
        this.id = id;
        this.relationType = relationType;
        this.parentTaskId = parentTaskId;
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public Long getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
