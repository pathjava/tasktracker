package ru.progwards.tasktracker.controller.dto;

/**
 * DtoPreview для TaskPriority
 * @author Pavel Khovaylo
 */
public class TaskPriorityDtoPreview {
    /**
     * идентификатор
     */
    private Long id;
    /**
     * имя
     */
    private String name;

    public TaskPriorityDtoPreview(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
