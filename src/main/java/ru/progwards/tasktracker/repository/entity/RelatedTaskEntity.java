package ru.progwards.tasktracker.repository.entity;

import java.util.Random;

/**
 * Сущность для хранения связанной задачи в БД
 *
 * @author Oleg Kiselev
 */
public class RelatedTaskEntity {

    private Long id;
    private RelationTypeEntity relationTypeEntity;
    private Long currentTaskId;
    private TaskEntity attachedTask;

    public RelatedTaskEntity() {
    }

    public RelatedTaskEntity(Long id, RelationTypeEntity relationTypeEntity, Long currentTaskId, TaskEntity attachedTask) {
        if (id == null) //TODO - for testing generate id
            id = new Random().nextLong();
        this.id = id;
        this.relationTypeEntity = relationTypeEntity;
        this.currentTaskId = currentTaskId;
        this.attachedTask = attachedTask;
    }

    public Long getId() {
        return id;
    }

    public RelationTypeEntity getRelationTypeEntity() {
        return relationTypeEntity;
    }

    public void setRelationTypeEntity(RelationTypeEntity relationTypeEntity) {
        this.relationTypeEntity = relationTypeEntity;
    }

    public Long getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(Long currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public TaskEntity getAttachedTask() {
        return attachedTask;
    }

    public void setAttachedTask(TaskEntity attachedTask) {
        this.attachedTask = attachedTask;
    }
}
