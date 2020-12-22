package ru.progwards.tasktracker.dto;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Объект, содержащий полные лог-данные (Журнала работ) о действиях в задаче
 *
 * @author Oleg Kiselev
 */
public class WorkLogDtoFull {

    private final Long id;
    private Long taskId;
    private Duration spent;
    private UserDtoPreview worker;
    private ZonedDateTime when;
    private String description;
    private String estimateChange;
    private Duration estimateValue;

    public WorkLogDtoFull(
            Long id, Long taskId, Duration spent, UserDtoPreview worker,
            ZonedDateTime when, String description, String estimateChange, Duration estimateValue
    ) {
        this.id = id;
        this.taskId = taskId;
        this.spent = spent;
        this.worker = worker;
        this.when = when;
        this.description = description;
        this.estimateChange = estimateChange;
        this.estimateValue = estimateValue;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimateChange() {
        return estimateChange;
    }

    public void setEstimateChange(String estimateChange) {
        this.estimateChange = estimateChange;
    }

    public Duration getEstimateValue() {
        return estimateValue;
    }

    public void setEstimateValue(Duration estimateValue) {
        this.estimateValue = estimateValue;
    }
}
