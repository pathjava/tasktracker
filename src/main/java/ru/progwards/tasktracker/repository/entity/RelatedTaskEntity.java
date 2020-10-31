package ru.progwards.tasktracker.repository.entity;

/**
 * сущность для хранения связанной задачи в БД
 *
 * @author Oleg Kiselev
 */
public class RelatedTaskEntity {

    private Long id;
    private RelationTypeEntity relationTypeEntity;
    private Long parentTaskId;
    private Long taskId;

    public RelatedTaskEntity(Long id, RelationTypeEntity relationTypeEntity, Long parentTaskId, Long taskId) {
        this.id = id;
        this.relationTypeEntity = relationTypeEntity;
        this.parentTaskId = parentTaskId;
        this.taskId = taskId;
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
