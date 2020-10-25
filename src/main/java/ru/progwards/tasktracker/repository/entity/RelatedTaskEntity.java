package ru.progwards.tasktracker.repository.entity;

public class RelatedTaskEntity {

    private Long id;
    private RelationTypeEntity relationTypeEntity;
    private Long parentTaskId;
    private TaskEntity task;

    public RelatedTaskEntity(Long id, RelationTypeEntity relationTypeEntity, Long parentTaskId, TaskEntity task) {
        this.id = id;
        this.relationTypeEntity = relationTypeEntity;
        this.parentTaskId = parentTaskId;
        this.task = task;
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

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }
}
