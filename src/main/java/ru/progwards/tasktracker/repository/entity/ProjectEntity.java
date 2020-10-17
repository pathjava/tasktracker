package ru.progwards.tasktracker.repository.entity;

import java.util.Arrays;
import java.util.Objects;

public class ProjectEntity {private final long id;
    private final String name;
    private final String description;
    private final String prefix;
    private final Long managerUserId;

    public ProjectEntity(long id, String name, String description, Long managerUserId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity entity = (ProjectEntity) o;
        return id == entity.id &&
                Objects.equals(name, entity.name) &&
                Objects.equals(description, entity.description) &&
                Objects.equals(prefix, entity.prefix) &&
                Objects.equals(managerUserId, entity.managerUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, prefix, managerUserId);
    }
}
