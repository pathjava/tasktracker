package ru.progwards.tasktracker.repository.entity;

import ru.progwards.tasktracker.service.vo.RelationType;
import ru.progwards.tasktracker.service.vo.Task;

public class RelatedTaskEntity {

    private Long id;
    private RelationType relationType;
    private Long parentTaskId;
    private Task task;

    public RelatedTaskEntity(Long id, RelationType relationType, Long parentTaskId, Task task) {
        this.id = id;
        this.relationType = relationType;
        this.parentTaskId = parentTaskId;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
