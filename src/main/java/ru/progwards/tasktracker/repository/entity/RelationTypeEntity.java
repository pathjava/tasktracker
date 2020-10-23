package ru.progwards.tasktracker.repository.entity;

import ru.progwards.tasktracker.service.vo.RelationType;

public class RelationTypeEntity {

    private Long id;
    private String name;
    private RelationType counterRelation;

    public RelationTypeEntity(Long id, String name, RelationType counterRelation) {
        this.id = id;
        this.name = name;
        this.counterRelation = counterRelation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelationType getCounterRelation() {
        return counterRelation;
    }

    public void setCounterRelation(RelationType counterRelation) {
        this.counterRelation = counterRelation;
    }
}
