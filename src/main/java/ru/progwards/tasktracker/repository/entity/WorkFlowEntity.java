package ru.progwards.tasktracker.repository.entity;

/**
 * Дерево движения задачи по статусам
 *
 * @author Gregory Lobkov
 */
public class WorkFlowEntity {

    Long id;

    /**
     * Ноименование
     */
    String name;

    /**
     * Признак шаблона
     *
     * Чтобы легко можно было отличить воркфлоу конкретного процесса
     * от шаблона, на основе которого будет все создаваться,
     * иначе его нельзя будет настраивать индивидуально
     */
    boolean pattern;

    /**
     * С какого статуса начинать движение задачи
     */
    Long start_status_id;

    public WorkFlowEntity() {
    }

    public WorkFlowEntity(Long id, String name, boolean pattern, Long start_status_id) {
        this.id = id;
        this.name = name;
        this.pattern = pattern;
        this.start_status_id = start_status_id;
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

    public boolean isPattern() {
        return pattern;
    }

    public void setPattern(boolean pattern) {
        this.pattern = pattern;
    }

    public Long getStart_status_id() {
        return start_status_id;
    }

    public void setStart_status_id(Long start_status_id) {
        this.start_status_id = start_status_id;
    }

}
