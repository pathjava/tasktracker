package ru.progwards.tasktracker.service.vo;

/**
 * value object - объект бизнес логики (тип задачи)
 *
 * @author Oleg Kiselev
 */
public class TaskType {

    private Long id;
    private Long project_id;
    private WorkFlow workFlow;
    private String name;

    public TaskType(Long id, Long project_id, WorkFlow workFlow, String name) {
        this.id = id;
        this.project_id = project_id;
        this.workFlow = workFlow;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
