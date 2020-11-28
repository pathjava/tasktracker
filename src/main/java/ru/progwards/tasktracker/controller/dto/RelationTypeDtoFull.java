package ru.progwards.tasktracker.controller.dto;

/**
 * Объект, содержащий полные данные об отношениях связанных задач
 *
 * @author Oleg Kiselev
 */
public class RelationTypeDtoFull {

    private final Long id;
    private String name;
    private RelationTypeDtoFull counterRelation;

    public RelationTypeDtoFull(
            Long id, String name, RelationTypeDtoFull counterRelation
    ) {
        this.id = id;
        this.name = name;
        this.counterRelation = counterRelation;
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

    public RelationTypeDtoFull getCounterRelation() {
        return counterRelation;
    }

    public void setCounterRelation(RelationTypeDtoFull counterRelation) {
        this.counterRelation = counterRelation;
    }
}
