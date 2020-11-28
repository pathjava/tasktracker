package ru.progwards.tasktracker.service.vo;

import java.time.ZonedDateTime;

/**
 * value object - это объект бизнес логики (комментарий)
 *
 * @author Konstantin Kishkin
 */

public class TaskNote {

    private Long id;
    private Long task_id;
    private User author;
    private User updater;
    private String comment;
    private ZonedDateTime created;
    private ZonedDateTime updated;

    public TaskNote(){

    }

    public TaskNote(Long id, Long task_id, User author, String comment) {
        this.id = id;
        this.task_id = task_id;
        this.author = author;
        this.comment = comment;
        this.created = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
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

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }
}
