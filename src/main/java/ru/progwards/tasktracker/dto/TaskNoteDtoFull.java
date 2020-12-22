package ru.progwards.tasktracker.dto;

import ru.progwards.tasktracker.model.User;

import java.time.ZonedDateTime;

/**
 * DtoPreview для TaskNote
 * @author Konstantin Kishkin
 */
public class TaskNoteDtoFull {

    private Long id;
    private User author;
    private User updater;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    private String comment;


    public TaskNoteDtoFull(Long id, User author, User updater, ZonedDateTime created, ZonedDateTime updated, String comment) {
        this.id = id;
        this.author = author;
        this.updater = updater;
        this.created = created;
        this.updated = updated;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getUpdater() {
        return updater;
    }

    public void setUpdater(User updater) {
        this.updater = updater;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

