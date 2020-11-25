package ru.progwards.tasktracker.controller.dto;

import ru.progwards.tasktracker.service.vo.Task;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * TODO НЕ ИСПОЛЬЗУЕТСЯ
 * Dto-объект проекта
 * @author Pavel Khovaylo
 */
public class ProjectDto {
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
    //TODO userDtoPreview
    private UserDto ownerDto;
    /**
     * время создания проекта
     */
    private ZonedDateTime created;
    /**
     * список задач, относящихся к данному проекту
     */
    //TODO TaskDtoPreview
    private List<Task> tasks;
    /**
     * хранит код последней добавленной задачи к данному проекту
     */
    //TODO delete
    private Long lastTaskCode;

    public ProjectDto(Long id, String name, String description, String prefix, UserDto ownerDto, ZonedDateTime created,
                      List<Task> tasks, Long lastTaskCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prefix = prefix;
        this.ownerDto = ownerDto;
        this.created = created;
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

    public UserDto getOwnerDto() {
        return ownerDto;
    }

    public void setOwnerDto(UserDto ownerDto) {
        this.ownerDto = ownerDto;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
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