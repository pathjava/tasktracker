package ru.progwards.tasktracker.dto;

/**
 * Объект, содержащий краткие данные задачи для отображения в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class TaskDtoPreview {

    private final Long id;
    private String code;
    private String name;
    private TaskTypeDtoPreview type;
    private TaskPriorityDtoPreview priority;

    public TaskDtoPreview(
            Long id, String code, String name, TaskTypeDtoPreview type, TaskPriorityDtoPreview priority
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.priority = priority;
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

    public TaskTypeDtoPreview getType() {
        return type;
    }

    public void setType(TaskTypeDtoPreview type) {
        this.type = type;
    }

    public TaskPriorityDtoPreview getPriority() {
        return priority;
    }

    public void setPriority(TaskPriorityDtoPreview priority) {
        this.priority = priority;
    }
}
