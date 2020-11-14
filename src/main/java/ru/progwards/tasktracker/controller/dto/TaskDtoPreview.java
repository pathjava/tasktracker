package ru.progwards.tasktracker.controller.dto;

/**
 * объект, содержащий краткое описание задачи для отображения в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class TaskDtoPreview {

    private final Long id;
    private String code;
    private String name;

    public TaskDtoPreview(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
