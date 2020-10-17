package ru.progwards.tasktracker.service.vo;

import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.util.List;

public class WorkFlow {
    private final Long id;
    private final String name;
    private final boolean pattern;
    private final List<WorkflowStatus> allowed;

    public WorkFlow(Long id, String name, boolean pattern, List<WorkflowStatus> allowed) {
        this.id = id;
        this.name = name;
        this.pattern = pattern;
        this.allowed = allowed;
    }
}
