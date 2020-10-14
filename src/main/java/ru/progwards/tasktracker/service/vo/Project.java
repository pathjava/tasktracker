package ru.progwards.tasktracker.service.vo;

public class Project {
    private final long id;
    private final String name;
    private final String description;
    private final long managerUserId;

    public Project(long id, String name, String description, long managerUserId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.managerUserId = managerUserId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getManagerUserId() {
        return managerUserId;
    }
}