package ru.progwards.tasktracker.repository.entity;

public class UserEntity {
    private final Long id;

    public UserEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
