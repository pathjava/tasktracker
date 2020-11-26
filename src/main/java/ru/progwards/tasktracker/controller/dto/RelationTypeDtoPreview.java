package ru.progwards.tasktracker.controller.dto;

/**
 * Объект, содержащий краткие данные об отношениях связанных задач
 *
 * @author Oleg Kiselev
 */
public class RelationTypeDtoPreview {

    private final Long id;
    private String name;

    public RelationTypeDtoPreview(Long id, String name) {
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
