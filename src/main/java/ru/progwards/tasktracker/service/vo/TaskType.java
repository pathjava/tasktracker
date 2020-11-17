package ru.progwards.tasktracker.service.vo;

/**
 * value object - объект бизнес логики (тип задачи)
 *
 * @author Oleg Kiselev
 */
public class TaskType {

    private final Long id;
    private Long project_id;
    private Long workFlow_id;
    private String name;

    public TaskType(Long id, Long project_id, Long workFlow_id, String name) {
        this.id = id;
        this.project_id = project_id;
        this.workFlow_id = workFlow_id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public Long getWorkFlow_id() {
        return workFlow_id;
    }

    public void setWorkFlow_id(Long workFlow_id) {
        this.workFlow_id = workFlow_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
