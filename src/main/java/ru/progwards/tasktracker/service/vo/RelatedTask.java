package ru.progwards.tasktracker.service.vo;

/**
 * value object - объект бизнес логики (связанная задача)
 *
 * @author Oleg Kiselev
 */
public class RelatedTask {

    private final Long id;
    private RelationType relationType;
    private Long currentTaskId;
    private Long attachedTaskId;

    public RelatedTask(
            Long id, RelationType relationType, Long currentTaskId, Long attachedTaskId
    ) {
        this.id = id;
        this.relationType = relationType;
        this.currentTaskId = currentTaskId;
        this.attachedTaskId = attachedTaskId;
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

    public Long getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(Long currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public Long getAttachedTaskId() {
        return attachedTaskId;
    }

    public void setAttachedTaskId(Long attachedTaskId) {
        this.attachedTaskId = attachedTaskId;
    }
}
