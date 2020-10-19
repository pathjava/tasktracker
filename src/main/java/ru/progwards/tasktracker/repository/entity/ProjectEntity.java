package ru.progwards.tasktracker.repository.entity;

public class ProjectEntity {
    private final Long id;
    private final String name;
    private final String description;
    private final String prefix;
    private final Long ownerId;
    private final Long created;
    private final Long workFlowId;

    public ProjectEntity(Long id, String name, String description, String prefix, Long ownerId, Long created, Long workFlowId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prefix = prefix;
        this.ownerId = ownerId;
        this.created = created;
        this.workFlowId = workFlowId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrefix() {
        return prefix;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getCreated() {
        return created;
    }

    public Long getWorkFlowId() {
        return workFlowId;
    }
}