package ru.progwards.tasktracker.controller.dto;

/**
 * Объект, содержащий данные об отношениях связанных задач
 *
 * @author Oleg Kiselev
 */
public class RelationTypeDto {

    private final Long id;
    private String name;
    private Long counterRelationId;

    public RelationTypeDto(Long id, String name, Long counterRelationId) {
        this.id = id;
        this.name = name;
        this.counterRelationId = counterRelationId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCounterRelationId() {
        return counterRelationId;
    }

    public void setCounterRelationId(Long counterRelationId) {
        this.counterRelationId = counterRelationId;
    }
}
