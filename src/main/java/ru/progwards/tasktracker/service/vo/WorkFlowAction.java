package ru.progwards.tasktracker.service.vo;

/**
 * Действия над задачей, переводящие её из одного состояния WorkFlowStatus в другое
 */
public class WorkFlowAction {

    Long id;

    /**
     * Родительский статус
     *
     * Статус задач, к которым применимо данное действие
     */
    Long parentStatus_id;

    /**
     * Наименование действия
     */
    String name;

    /**
     * Идентификатор статуса, в который переводится задача после применения действия
     */
    Long status_id;

    /**
     * Статус, в который переводится задача после применения действия
     */
    WorkFlowStatus status;


    public WorkFlowAction(Long id, Long parentStatus_id, String name, Long status_id, WorkFlowStatus status) {
        this.id = id;
        this.parentStatus_id = parentStatus_id;
        this.name = name;
        this.status_id = status_id;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentStatus_id() {
        return parentStatus_id;
    }

    public void setParentStatus_id(Long parentStatus_id) {
        this.parentStatus_id = parentStatus_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }

    public WorkFlowStatus getStatus() {
        return status;
    }

    public void setStatus(WorkFlowStatus status) {
        this.status = status;
    }

}
