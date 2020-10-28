package ru.progwards.tasktracker.repository.entity;

public class ProjectEntity {
    private final Long id;

    public ProjectEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
