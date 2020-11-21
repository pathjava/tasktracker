package ru.progwards.tasktracker.repository.entity;

import ru.progwards.tasktracker.service.vo.User;

import java.util.Random;

/**
 * Сущность для хранения лога (Журнала работ) в БД
 *
 * @author Oleg Kiselev
 */
public class WorkLogEntity {

    private Long id;
    private Long taskId;
    private Long spent;
    private User worker;
    private Long when;
    private String description;

    public WorkLogEntity() {
    }

    public WorkLogEntity(
            Long id, Long taskId, Long spent,
            User worker, Long when, String description
    ) {
        if (id == null) //TODO - for testing generate id
            id = new Random().nextLong();
        this.id = id;
        this.taskId = taskId;
        this.spent = spent;
        this.worker = worker;
        this.when = when;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getSpent() {
        return spent;
    }

    public void setSpent(Long spent) {
        this.spent = spent;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public Long getWhen() {
        return when;
    }

    public void setWhen(Long when) {
        this.when = when;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
