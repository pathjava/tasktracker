package ru.progwards.tasktracker.repository.entity;

import java.util.Arrays;

public class ProjectEntity {
    private final Long id;
    private final String name;
    private final String description;
    private final String prefix;
    private final Long ownerId;
    private final Long created;
    private final Long workFlowId;

    public ProjectEntity(Long id, String name, String description, Long ownerId, Long created, Long workFlowId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prefix = getPrefix(name);
        this.ownerId = ownerId;
        this.created = created;
        this.workFlowId = workFlowId;
    }

    public static String getPrefix(String name) {
        String[] items = name.split("\\s+");
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(items).forEach(e -> stringBuilder.append(e.substring(0, 1).toUpperCase()));
        return stringBuilder.toString();
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