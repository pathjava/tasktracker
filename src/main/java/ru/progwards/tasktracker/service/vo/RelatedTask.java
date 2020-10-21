package ru.progwards.tasktracker.service.vo;

public class RelatedTask {
    private final Long id;
    private RelationType relationType;
    private Task task;

    public RelatedTask(Long id, RelationType relationType, Task task) {
        this.id = id;
        this.relationType = relationType;
        this.task = task;
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

    public Task getTask() {
        return task;
    }

//    public void setTask(Task task) {
//        this.task = task;
//    }
}
