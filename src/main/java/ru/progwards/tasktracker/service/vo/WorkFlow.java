package ru.progwards.tasktracker.service.vo;

import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.util.List;

public class WorkFlow {
    private Long id;
    private String name;
    private boolean pattern;
    private List<WorkflowStatus> allowed;

    public WorkFlow(Long id, String name, boolean pattern, List<WorkflowStatus> allowed) {
        this.id = id;
        this.name = name;
        this.pattern = pattern;
        this.allowed = allowed;
    }

    public WorkFlow(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPattern() {
        return pattern;
    }

    public List<WorkflowStatus> getAllowed() {
        return allowed;
    }
}
