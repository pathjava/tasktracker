package ru.progwards.tasktracker.controller.dto;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * @author Oleg Kiselev
 */
public class WorkLogDtoPreview {

    private final Long id;
    private Duration spent;
    private UserDto worker;
    private ZonedDateTime when;

    public WorkLogDtoPreview(Long id, Duration spent, UserDto worker, ZonedDateTime when) {
        this.id = id;
        this.spent = spent;
        this.worker = worker;
        this.when = when;
    }

    public Long getId() {
        return id;
    }

    public Duration getSpent() {
        return spent;
    }

    public void setSpent(Duration spent) {
        this.spent = spent;
    }

    public UserDto getWorker() {
        return worker;
    }

    public void setWorker(UserDto worker) {
        this.worker = worker;
    }

    public ZonedDateTime getWhen() {
        return when;
    }

    public void setWhen(ZonedDateTime when) {
        this.when = when;
    }
}
