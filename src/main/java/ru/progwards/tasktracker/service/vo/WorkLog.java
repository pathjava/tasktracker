package ru.progwards.tasktracker.service.vo;

import ru.progwards.tasktracker.util.types.EstimateChange;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 *
 *
 * @author Oleg Kiselev
 */
public class WorkLog {

    private Long id;
    private Long taskId;
    private Duration spent;
    private User worker;
    private ZonedDateTime when;
    private String description;
    private EstimateChange estimateChange;
    private Duration estimateValue;

    public WorkLog(
            Long id, Long taskId, Duration spent, User worker,
            ZonedDateTime when, String description,
            EstimateChange estimateChange, Duration estimateValue
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

    public void setId(Long id) {
        this.id = id;
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

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
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

    public EstimateChange getEstimateChange() {
        return estimateChange;
    }

    public void setEstimateChange(EstimateChange estimateChange) {
        this.estimateChange = estimateChange;
    }

    public Duration getEstimateValue() {
        return estimateValue;
    }

    public void setEstimateValue(Duration estimateValue) {
        this.estimateValue = estimateValue;
    }
}
