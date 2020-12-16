package ru.progwards.tasktracker.repository.entity;

import java.util.Random;

/**
 * Сущность для хранения лога (Журнала работ) в БД
 *
 * @author Oleg Kiselev
 */
public class WorkLogEntity {

    private Long id;
    private TaskEntity task;
    private Long spent;
    private UserEntity worker;
    private Long when;
    private String description;

    public WorkLogEntity() {
    }

    public WorkLogEntity(
            Long id, TaskEntity task, Long spent,
            UserEntity worker, Long when, String description
    ) {
        if (id == null) //TODO - for testing generate id
            id = new Random().nextLong();
        this.id = id;
        this.task = task;
        this.spent = spent;
        this.worker = worker;
        this.when = when;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public Long getSpent() {
        return spent;
    }

    public void setSpent(Long spent) {
        this.spent = spent;
    }

    public UserEntity getWorker() {
        return worker;
    }

    public void setWorker(UserEntity worker) {
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
