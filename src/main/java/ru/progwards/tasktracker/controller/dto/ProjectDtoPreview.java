package ru.progwards.tasktracker.controller.dto;

/**
 * DtoPreview для проекта
 * @author Pavel Khovaylo
 */
public class ProjectDtoPreview {
    /**
     * идентификатор проекта
     */
    private Long id;
    /**
     * имя проекта
     */
    private String name;

    public ProjectDtoPreview(Long id, String name) {
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