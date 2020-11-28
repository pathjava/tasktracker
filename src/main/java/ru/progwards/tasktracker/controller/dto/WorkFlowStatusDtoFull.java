package ru.progwards.tasktracker.controller.dto;

import ru.progwards.tasktracker.service.vo.WorkFlowAction;
import ru.progwards.tasktracker.util.types.WorkFlowState;

import java.util.List;

/**
 * Статус, в который может быть переведена задача по ходу WorkFlowEntity
 *
 * @author Gregory Lobkov
 */
public class WorkFlowStatusDtoFull {

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
     * На данный статус задачу можно переводить из любого состояния
     */
    Boolean alwaysAllow;

    /**
     * Действия, в которые могут быть применены к задаче с данным статусом
     */
    List<WorkFlowAction> actions;

    public WorkFlowStatusDtoFull(Long id, Long workflow_id, String name, WorkFlowState state, Boolean alwaysAllow, List<WorkFlowAction> actions) {
        this.id = id;
        this.workflow_id = workflow_id;
        this.name = name;
        this.state = state;
        this.alwaysAllow = alwaysAllow;
        this.actions = actions;
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

    public Boolean getAlwaysAllow() {
        return alwaysAllow;
    }

    public void setAlwaysAllow(Boolean alwaysAllow) {
        this.alwaysAllow = alwaysAllow;
    }


    public List<WorkFlowAction> getActions() {
        return actions;
    }

    public void setActions(List<WorkFlowAction> actions) {
        this.actions = actions;
    }

}
