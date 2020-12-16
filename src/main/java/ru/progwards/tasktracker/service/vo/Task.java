package ru.progwards.tasktracker.service.vo;

import lombok.Data;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * value object - объект бизнес логики (задача)
 *
 * @author Oleg Kiselev
 */
@Entity
@Table(name = "task")
public class Task {

    @Id
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    @GeneratedValue(generator = "task_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TaskType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private TaskPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private User executor;

    @Column(nullable = false)
    private ZonedDateTime created;
    private ZonedDateTime updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private WorkFlowStatus status;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<WorkFlowAction> actions;

    private Duration estimation;
    private Duration timeSpent;
    private Duration timeLeft;

    @OneToMany(mappedBy = "currentTask", fetch = FetchType.LAZY)
    private List<RelatedTask> relatedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "attachedTask", fetch = FetchType.LAZY)
    private List<RelatedTask> relatedTasksAttached = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskAttachment> attachments;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<WorkLog> workLogs;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskNote> notes;

    /*boolean isDeleted;*/ //TODO

    public Task(
            Long id, String code, String name, String description,
            TaskType type, TaskPriority priority, Project project, User author, User executor,
            ZonedDateTime created, ZonedDateTime updated, WorkFlowStatus status, List<WorkFlowAction> actions,
            Duration estimation, Duration timeSpent, Duration timeLeft,
            List<RelatedTask> relatedTasks, List<RelatedTask> relatedTasksAttached,
            List<TaskAttachment> attachments, List<WorkLog> workLogs, List<TaskNote> notes
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.project = project;
        this.author = author;
        this.executor = executor;
        this.created = created;
        this.updated = updated;
        this.status = status;
        this.actions = actions;
        this.estimation = estimation;
        this.timeSpent = timeSpent;
        this.timeLeft = timeLeft;
        this.relatedTasks = relatedTasks;
        this.relatedTasksAttached = relatedTasksAttached;
        this.attachments = attachments;
        this.workLogs = workLogs;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public WorkFlowStatus getStatus() {
        return status;
    }

    public void setStatus(WorkFlowStatus status) {
        this.status = status;
    }

    public List<WorkFlowAction> getActions() {
        return actions;
    }

    public void setActions(List<WorkFlowAction> actions) {
        this.actions = actions;
    }

    public Duration getEstimation() {
        return estimation;
    }

    public void setEstimation(Duration estimation) {
        this.estimation = estimation;
    }

    public Duration getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Duration timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Duration getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Duration timeLeft) {
        this.timeLeft = timeLeft;
    }

    public List<RelatedTask> getRelatedTasks() {
        return relatedTasks;
    }

    public void setRelatedTasks(List<RelatedTask> relatedTasks) {
        this.relatedTasks = relatedTasks;
    }

    public List<RelatedTask> getRelatedTasksAttached() {
        return relatedTasksAttached;
    }

    public void setRelatedTasksAttached(List<RelatedTask> counterRelatedTasks) {
        this.relatedTasksAttached = counterRelatedTasks;
    }

    public List<TaskAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TaskAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<WorkLog> getWorkLogs() {
        return workLogs;
    }

    public void setWorkLogs(List<WorkLog> workLogs) {
        this.workLogs = workLogs;
    }

    public List<TaskNote> getNotes() {
        return notes;
    }

    public void setNotes(List<TaskNote> notes) {
        this.notes = notes;
    }
}
