package ru.progwards.tasktracker.service.vo;

import ru.progwards.tasktracker.util.types.WorkFlowState;

import java.util.List;

/**
 * Статус, в который может быть переведена задача по ходу WorkFlowEntity
 *
 * @author Gregory Lobkov
 */
public class WorkFlowStatus {

    Long id;

    /**
     * Родительский WF
     */
    Long workflow_id;

    /**
     * Наименование
     */
    String name;

    /**
     * Состояние задачи
     */
    WorkFlowState state;

    /**
     * Действия, в которые могут быть применены к задаче с данным статусом
     */
    List<WorkFlowAction> actions;

    /**
     * На данный статус задачу можно переводить из любого состояния
     */
    Boolean allowFromAnyStatus;


    public WorkFlowStatus(Long id, Long workflow_id, String name, WorkFlowState state, List<WorkFlowAction> actions, Boolean allowFromAnyStatus) {
        this.id = id;
        this.workflow_id = workflow_id;
        this.name = name;
        this.state = state;
        this.actions = actions;
        this.allowFromAnyStatus = allowFromAnyStatus;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkflow_id() {
        return workflow_id;
    }

    public void setWorkflow_id(Long workflow_id) {
        this.workflow_id = workflow_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkFlowState getState() {
        return state;
    }

    public void setState(WorkFlowState state) {
        this.state = state;
    }

    public List<WorkFlowAction> getActions() {
        return actions;
    }

    public void setActions(List<WorkFlowAction> actions) {
        this.actions = actions;
    }

    public Boolean getAllowFromAnyStatus() {
        return allowFromAnyStatus;
    }

    public void setAllowFromAnyStatus(Boolean allowFromAnyStatus) {
        this.allowFromAnyStatus = allowFromAnyStatus;
    }

}
