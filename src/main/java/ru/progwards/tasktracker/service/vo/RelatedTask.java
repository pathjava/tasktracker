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
    private Task attachedTask;

    public RelatedTask(Long id, RelationType relationType, Long currentTaskId, Task attachedTask) {
        this.id = id;
        this.relationType = relationType;
        this.currentTaskId = currentTaskId;
        this.attachedTask = attachedTask;
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

    public Task getAttachedTask() {
        return attachedTask;
    }

    public void setAttachedTask(Task attachedTask) {
        this.attachedTask = attachedTask;
    }
}
