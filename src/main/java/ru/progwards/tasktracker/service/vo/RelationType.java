package ru.progwards.tasktracker.service.vo;

/**
 * value object - объект бизнес логики (тип связи)
 *
 * @author Oleg Kiselev
 */
public class RelationType {

    private final Long id;
    private String name;
    private RelationType counterRelation;

    public RelationType(Long id, String name, RelationType counterRelation) {
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

    public RelationType getCounterRelation() {
        return counterRelation;
    }

    public void setCounterRelation(RelationType counterRelation) {
        this.counterRelation = counterRelation;
    }
}
