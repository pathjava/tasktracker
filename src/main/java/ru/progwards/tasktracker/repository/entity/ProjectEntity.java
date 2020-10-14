package ru.progwards.tasktracker.repository.entity;

import java.util.Arrays;

public class ProjectEntity {private final long id;
    private final String name;
    private final String description;
    private final String prefix;
    private final long managerUserId;

    public ProjectEntity(long id, String name, String description, long managerUserId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prefix = getPrefix(name);
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

    public String getPrefix() {
        return prefix;
    }

    public long getManagerUserId() {
        return managerUserId;
    }

    public static String getPrefix(String name) {
        String[] items = name.split("\\s+");
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(items).forEach(e -> stringBuilder.append(e.substring(0, 1).toUpperCase()));
        return stringBuilder.toString();
    }
}
