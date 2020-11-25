package ru.progwards.tasktracker.service.vo;

/**
 * value object - объект бизнес логики (тип связи)
 *
 * @author Oleg Kiselev
 */
public class RelationType {

    private final Long id;
    private String name;
    private Long counterRelationId;

    public RelationType(Long id, String name, Long counterRelationId) {
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
