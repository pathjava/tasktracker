package ru.progwards.tasktracker.controller.dto;

import ru.progwards.tasktracker.service.vo.User;

/**
 * DtoFull для TaskNote
 * @author Konstantin Kishkin
 */

public class TaskNoteDtoFull {

    private User author;
    private User updater;
    private String comment;

    public TaskNoteDtoFull(User author, User updater, String comment) {
        this.author = author;
        this.updater = updater;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
