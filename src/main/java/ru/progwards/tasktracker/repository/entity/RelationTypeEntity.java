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
    private RelationTypeEntity counterRelation;

    public RelationTypeEntity() {
    }

    public RelationTypeEntity(Long id, String name, RelationTypeEntity counterRelation) {
        if (id == null) //TODO - for testing generate id
            id = new Random().nextLong();
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

    public RelationTypeEntity getCounterRelation() {
        return counterRelation;
    }

    public void setCounterRelation(RelationTypeEntity counterRelation) {
        this.counterRelation = counterRelation;
    }
}
