package ru.progwards.tasktracker.repository.deprecated.entity;

import java.util.Random;

/**
 * Сущность для хранения связанной задачи в БД
 *
 * @author Oleg Kiselev
 */
@Deprecated
public class RelatedTaskEntity {

    private Long id;
    private RelationTypeEntity relationTypeEntity;
    private TaskEntity currentTask;
    private TaskEntity attachedTask;
    private boolean isDeleted;

    public RelatedTaskEntity() {
    }

    public RelatedTaskEntity(
            Long id, RelationTypeEntity relationTypeEntity, TaskEntity currentTask,
            TaskEntity attachedTask, boolean isDeleted
    ) {
        if (id == null) //TODO - for testing generate id
            id = new Random().nextLong();
        this.id = id;
        this.relationTypeEntity = relationTypeEntity;
        this.currentTask = currentTask;
        this.attachedTask = attachedTask;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationTypeEntity getRelationTypeEntity() {
        return relationTypeEntity;
    }

    public void setRelationTypeEntity(RelationTypeEntity relationTypeEntity) {
        this.relationTypeEntity = relationTypeEntity;
    }

    public TaskEntity getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(TaskEntity currentTask) {
        this.currentTask = currentTask;
    }

    public TaskEntity getAttachedTask() {
        return attachedTask;
    }

    public void setAttachedTask(TaskEntity attachedTask) {
        this.attachedTask = attachedTask;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
