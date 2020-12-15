package ru.progwards.tasktracker.service.vo;

import java.util.List;

/**
 * Бизнес-процесс
 * Формирует дерево движения задачи по статусам
 *
 * @author Gregory Lobkov
 */
public class WorkFlow {

    Long id;

    /**
     * Ноименование
     */
    String name;

    /**
     * Признак шаблона
     *
     * Чтобы легко можно было отличить воркфлоу конкретного процесса
     * от шаблона, на основе которого будет все создаваться,
     * иначе его нельзя будет настраивать индивидуально
     */
    boolean pattern;

    /**
     * С какого статуса начинать движение задачи, идентификатор
     */
    Long start_status_id;

    /**
     * С какого статуса начинать движение задачи
     */
    WorkFlowStatus startStatus;

    /**
     * Статусы, возможные по бизнес-процессу
     */
    List<WorkFlowStatus> statuses;

    List<TaskType> taskTypes;

    public WorkFlow() { }

    public WorkFlow(Long id, String name, boolean pattern, Long start_status_id, WorkFlowStatus startStatus, List<WorkFlowStatus> statuses, List<TaskType> taskTypes) {
        this.id = id;
        this.name = name;
        this.pattern = pattern;
        this.start_status_id = start_status_id;
        this.startStatus = startStatus;
        this.statuses = statuses;
        this.taskTypes = taskTypes;
    }

    public List<WorkFlowStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<WorkFlowStatus> statuses) {
        this.statuses = statuses;
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

    public boolean isPattern() {
        return pattern;
    }

    public void setPattern(boolean pattern) {
        this.pattern = pattern;
    }

    public Long getStart_status_id() {
        return start_status_id;
    }

    public void setStart_status_id(Long start_status_id) {
        this.start_status_id = start_status_id;
    }

    public WorkFlowStatus getStartStatus() {
        return startStatus;
    }

    public void setStartStatus(WorkFlowStatus startStatus) {
        this.startStatus = startStatus;
    }

    public List<TaskType> getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(List<TaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }
}
