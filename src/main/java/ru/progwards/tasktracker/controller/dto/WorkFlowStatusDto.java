package ru.progwards.tasktracker.controller.dto;

import ru.progwards.tasktracker.service.vo.WorkFlowState;

import java.util.List;

/**
 * Статус, в который может быть переведена задача по ходу WorkFlowEntity
 *
 * @author Gregory Lobkov
 */
public class WorkFlowStatusDto {

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
    Boolean allowFromAnyStatus;


    public WorkFlowStatusDto(Long id, Long workflow_id, String name, WorkFlowState state, Boolean allowFromAnyStatus) {
        this.id = id;
        this.workflow_id = workflow_id;
        this.name = name;
        this.state = state;
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

    public Boolean getAllowFromAnyStatus() {
        return allowFromAnyStatus;
    }

    public void setAllowFromAnyStatus(Boolean allowFromAnyStatus) {
        this.allowFromAnyStatus = allowFromAnyStatus;
    }

}
