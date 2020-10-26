package ru.progwards.tasktracker.service.vo;

public class WorkFlow {
    private Long id;
    private String name;
    private boolean pattern;

    public WorkFlow(Long id, String name, boolean pattern) {
        this.id = id;
        this.name = name;
        this.pattern = pattern;
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
}
