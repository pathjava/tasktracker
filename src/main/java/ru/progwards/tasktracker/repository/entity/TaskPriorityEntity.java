package ru.progwards.tasktracker.repository.entity;

/**
 * Класс TaskPriorityEntity - сущность репозитория
 * @author Pavel Khovaylo
 */
public class TaskPriorityEntity {
    private Long id;
    private String name;
    private Integer value;

    public TaskPriorityEntity(Long id, String name, Integer value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}