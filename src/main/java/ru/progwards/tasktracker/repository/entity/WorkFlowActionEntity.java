package ru.progwards.tasktracker.repository.entity;

/**
 * Действия над задачей, переводящие её из одного состояния WorkFlowStatus в другое
 *
 * @author Gregory Lobkov
 */
public class WorkFlowActionEntity {

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
     * Статус, в который переводится задача после применения действия
     */
    Long next_status_id;

    public WorkFlowActionEntity() {
    }

    public WorkFlowActionEntity(Long id, Long parentStatus_id, String name, Long next_status_id) {
        this.id = id;
        this.parentStatus_id = parentStatus_id;
        this.name = name;
        this.next_status_id = next_status_id;
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

    public Long getNext_status_id() {
        return next_status_id;
    }

    public void setNext_status_id(Long next_status_id) {
        this.next_status_id = next_status_id;
    }

}
