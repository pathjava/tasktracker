package ru.progwards.tasktracker.service.vo;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * value object - это объект бизнес логики (комментарий)
 *
 * @author Konstantin Kishkin
 */

@Entity
@Table(name="tasknote")
public class TaskNote {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name="task_id")
    private Long task;

    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name="user_id")
    private User author;

    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name="user_id")
    private User updater;

    private String comment;
    private ZonedDateTime created;
    private ZonedDateTime updated;

    public TaskNote(){

    }

    public TaskNote(Long id, Long task_id, User author, String comment) {
        ZonedDateTime date = ZonedDateTime.now();
        this.id = id;
        this.task = task_id;
        this.author = author;
        this.comment = comment;
        this.created = date;
        this.updated = date;
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

    public Long getTask() {
        return task;
    }

    public void setTask(Long task) {
        this.task = task;
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
