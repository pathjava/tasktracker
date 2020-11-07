package ru.progwards.tasktracker.controller.dto;

import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Dto-объект проекта
 * @author Pavel Khovaylo
 */
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private String prefix;
    private User owner;
    private Long ownerId;
    private ZonedDateTime created;
    private WorkFlow workFlow;
    private Long workFlowId;
    private List<Task> tasks;
    private Long lastTaskCode;

    public ProjectDto(Long id, String name, String description, String prefix, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prefix = prefix;
        this.owner = owner;
        this.ownerId = owner.getId();
        this.created = ZonedDateTime.now();
        this.workFlow = new WorkFlow(id, null, false, null, null);
        this.workFlowId = workFlow.getId();
        this.tasks = new ArrayList<>();
        this.lastTaskCode = 0L;
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

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Long workFlowId) {
        this.workFlowId = workFlowId;
    }
}