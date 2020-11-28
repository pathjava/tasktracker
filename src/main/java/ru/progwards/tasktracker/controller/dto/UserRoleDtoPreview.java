package ru.progwards.tasktracker.controller.dto;

public class UserRoleDtoPreview {
    private Long id;
    private String name;

    public UserRoleDtoPreview(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
