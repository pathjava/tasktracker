package ru.progwards.tasktracker.util.types;

public class WorkFlowStatus {

    private Long id;

    public WorkFlowStatus(Long id) {
        this.id = id;
    }

    public WorkFlowStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
