package ru.progwards.tasktracker.repository.entity;

import java.util.Random;

/**
 * Сущность для хранения связей в БД
 *
 * @author Oleg Kiselev
 */
public class RelationTypeEntity {

    private Long id;
    private String name;
    private Long counterRelationId;

    public RelationTypeEntity() {
    }

    public RelationTypeEntity(Long id, String name, Long counterRelationId) {
        if (id == null) //TODO - for testing generate id
            id = new Random().nextLong();
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
