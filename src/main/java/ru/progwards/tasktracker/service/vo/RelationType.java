package ru.progwards.tasktracker.service.vo;

public class RelationType {
    private final Long id;
    private final String name;

    public RelationType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
