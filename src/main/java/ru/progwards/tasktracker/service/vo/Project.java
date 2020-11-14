package ru.progwards.tasktracker.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

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
     * идентификатор владельца (создателя)
     */
    private Long ownerId;
    /**
     * время создания проекта
     */
    private ZonedDateTime created;
    /**
     * стадия разработки, в которой находится проект
     */
    private WorkFlow workFlow;
    /**
     * идентификатор WorkFlow
     */
    private Long workFlowId;
    /**
     * список задач, относящихся к данному проекту
     */
    private List<Task> tasks;
    /**
     * хранит код последней добавленной задачи к данному проекту
     */
    private Long lastTaskCode;

    public Project(Long id, String name, String description, String prefix, User owner,
                   ZonedDateTime created, WorkFlow workFlow, List<Task> tasks, Long lastTaskCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prefix = prefix;
        this.owner = owner;
        //TODO для тестирования
        this.ownerId = (owner==null) ? 1L : owner.getId();
        this.created = created;
        this.workFlow = workFlow;
        this.workFlowId = workFlow.getId();
        this.tasks = tasks;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public Long getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Long workFlowId) {
        this.workFlowId = workFlowId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Long getLastTaskCode() {
        return lastTaskCode;
    }

    public void setLastTaskCode(Long lastTaskCode) {
        this.lastTaskCode = lastTaskCode;
    }
}