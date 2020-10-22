package ru.progwards.tasktracker.service.vo;

public class User {
    private final Long id;

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
