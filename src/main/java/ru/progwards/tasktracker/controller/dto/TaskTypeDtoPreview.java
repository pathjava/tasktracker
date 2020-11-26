package ru.progwards.tasktracker.controller.dto;

/**
 * Объект, содержащий краткие данные о типе задачи, выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class TaskTypeDtoPreview {

    private final Long id;
    private String name;

    public TaskTypeDtoPreview(Long id, String name) {
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
