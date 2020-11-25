package ru.progwards.tasktracker.service.vo;

/**
 * value object - объект бизнес логики (тип задачи)
 *
 * @author Oleg Kiselev
 */
public class TaskType {

    private final Long id;
    private WorkFlow workFlow;
    private String name;

    public TaskType(Long id, WorkFlow workFlow, String name) {
        this.id = id;
        this.workFlow = workFlow;
        this.name = name;
    }

    public Long getId() {
        return id;
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
