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
    private Long parentTaskId;
    private Long taskId;

    public RelatedTaskEntity() {
    }

    public RelatedTaskEntity(Long id, RelationTypeEntity relationTypeEntity, Long parentTaskId, Long taskId) {
        if (id == null) //TODO - for testing generate id
            id = new Random().nextLong();
        this.id = id;
        this.relationTypeEntity = relationTypeEntity;
        this.parentTaskId = parentTaskId;
        this.taskId = taskId;
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
