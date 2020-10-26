package ru.progwards.tasktracker.repository.entity;

import java.util.List;

/**
 * Статус, в который может быть переведена задача по ходу WorkFlowEntity
 *
 * @author Gregory Lobkov
 */
public class WorkFlowStatusEntity {

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
    String state;

    /**
     * На данный статус задачу можно переводить из любого состояния
     */
    Boolean alwaysAllow;


    public WorkFlowStatusEntity() {
    }

    public WorkFlowStatusEntity(Long id, Long workflow_id, String name, String state, Boolean alwaysAllow) {
        this.id = id;
        this.workflow_id = workflow_id;
        this.name = name;
        this.state = state;
        this.alwaysAllow = alwaysAllow;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getAlwaysAllow() {
        return alwaysAllow;
    }

    public void setAlwaysAllow(Boolean alwaysAllow) {
        this.alwaysAllow = alwaysAllow;
    }

}
