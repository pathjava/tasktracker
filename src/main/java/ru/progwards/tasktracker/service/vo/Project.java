package ru.progwards.tasktracker.service.vo;

public class Project {

    private Long id;

    public Project(Long id) {
        this.id = id;
    }

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
