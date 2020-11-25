package ru.progwards.tasktracker.service.vo;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Класс Project - бизнес-модель проекта
 * @author Pavel Khovaylo
 */
public class Project  {
    /**
     * идентификатор проекта
     */
    private Long id;
    /**
     * имя проекта
     */
    private String name;
    /**
     * описание проекта
     */
    private String description;
    /**
     * уникальная аббревиатура, созданная на основании имени проекта
     */
    private String prefix;
    /**
     * владелец (создатель) проекта
     */
    private User owner;
    /**
     * время создания проекта
     */
    private ZonedDateTime created;
    /**
     * список типов задач, относящихся к данному проекту
     */
    private List<TaskType> taskTypes;
    /**
     * хранит код последней добавленной задачи к данному проекту
     */
    private Long lastTaskCode;

    public Project(Long id, String name, String description, String prefix, User owner, ZonedDateTime created,
                   List<TaskType> taskTypes, Long lastTaskCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prefix = prefix;
        this.owner = owner;
        this.created = created;
        this.taskTypes = taskTypes;
        this.lastTaskCode = lastTaskCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public List<TaskType> getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(List<TaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }

    public Long getLastTaskCode() {
        return lastTaskCode;
    }

    public void setLastTaskCode(Long lastTaskCode) {
        this.lastTaskCode = lastTaskCode;
    }
}