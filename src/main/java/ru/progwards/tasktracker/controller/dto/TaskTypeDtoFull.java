package ru.progwards.tasktracker.controller.dto;

import ru.progwards.tasktracker.service.vo.WorkFlow;

/**
 * Объект, содержащий данные, выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class TaskTypeDtoFull {

    private final Long id;
    private WorkFlow workFlow;
    private String name;

    public TaskTypeDtoFull(Long id, WorkFlow workFlow, String name) {
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
