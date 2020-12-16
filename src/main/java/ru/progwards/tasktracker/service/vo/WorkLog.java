package ru.progwards.tasktracker.service.vo;

import ru.progwards.tasktracker.util.types.EstimateChange;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * value object - объект бизнес логики (лог) (Журнала работ)
 *
 * @author Oleg Kiselev
 */
@Entity
@Table(name = "work_logs")
public class WorkLog {

    @Id
    @SequenceGenerator(name = "work_log_seq", sequenceName = "work_logs_seq", allocationSize = 1)
    @GeneratedValue(generator = "work_log_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @Column(name = "spent")
    private Duration spent;

    @ManyToOne //TODO - fetch - ?
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User worker;

    @Column(name = "when", nullable = false)
    private ZonedDateTime when;

    @Column(name = "description")
    private String description;

    //TODO - enum - ?
    @Column(name = "estimate_change", nullable = false)
    private EstimateChange estimateChange;

    @Column(name = "estimate_value")
    private Duration estimateValue;

    public WorkLog(
            Long id, Task task, Duration spent, User worker,
            ZonedDateTime when, String description,
            EstimateChange estimateChange, Duration estimateValue
    ) {
        this.id = id;
        this.task = task;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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
