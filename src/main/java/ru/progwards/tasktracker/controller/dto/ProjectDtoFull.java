package ru.progwards.tasktracker.controller.dto;

import java.time.ZonedDateTime;

/**
 * DtoFull для проекта
 * @author Pavel Khovaylo
 */
public class ProjectDtoFull {
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
    private UserDto owner;
    /**
     * время создания проекта
     */
    private ZonedDateTime created;

    public ProjectDtoFull(Long id, String name, String description, String prefix, UserDto owner,
                          ZonedDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prefix = prefix;
        this.owner = owner;
        this.created = created;
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

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }
}