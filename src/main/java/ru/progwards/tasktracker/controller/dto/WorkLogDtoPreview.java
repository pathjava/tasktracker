package ru.progwards.tasktracker.controller.dto;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Объект, содержащий краткие лог-данные (Журнала работ) о действиях в задаче
 *
 * @author Oleg Kiselev
 */
public class WorkLogDtoPreview {

    private final Long id;
    private Duration spent;
    private UserDtoPreview worker;
    private ZonedDateTime when;

    public WorkLogDtoPreview(
            Long id, Duration spent, UserDtoPreview worker, ZonedDateTime when
    ) {
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

    public UserDtoPreview getWorker() {
        return worker;
    }

    public void setWorker(UserDtoPreview worker) {
        this.worker = worker;
    }

    public ZonedDateTime getWhen() {
        return when;
    }

    public void setWhen(ZonedDateTime when) {
        this.when = when;
    }
}
