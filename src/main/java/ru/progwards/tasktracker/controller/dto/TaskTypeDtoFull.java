package ru.progwards.tasktracker.controller.dto;

/**
 * Объект, содержащий полные данные о типе задачи, выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class TaskTypeDtoFull {

    private final Long id;
    private WorkFlowDtoPreview workFlow;
    private String name;

    public TaskTypeDtoFull(
            Long id, WorkFlowDtoPreview workFlow, String name
    ) {
        this.id = id;
        this.workFlow = workFlow;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public WorkFlowDtoPreview getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlowDtoPreview workFlow) {
        this.workFlow = workFlow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
