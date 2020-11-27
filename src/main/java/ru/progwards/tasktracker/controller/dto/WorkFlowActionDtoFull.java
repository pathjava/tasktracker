package ru.progwards.tasktracker.controller.dto;

/**
 * Действия над задачей, переводящие её из одного состояния WorkFlowStatus в другое
 *
 * @author Gregory Lobkov
 */
public class WorkFlowActionDtoFull {

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
    Long nextStatus_id;


    public WorkFlowActionDtoFull(Long id, Long parentStatus_id, String name, Long nextStatus_id) {
        this.id = id;
        this.parentStatus_id = parentStatus_id;
        this.name = name;
        this.nextStatus_id = nextStatus_id;
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

    public Long getNextStatus_id() {
        return nextStatus_id;
    }

    public void setNextStatus_id(Long nextStatus_id) {
        this.nextStatus_id = nextStatus_id;
    }

}
