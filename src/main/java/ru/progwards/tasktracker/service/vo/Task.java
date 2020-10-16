package ru.progwards.tasktracker.service.vo;

import ru.progwards.tasktracker.util.types.TaskPriority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkFlowStatus;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

public class Task {

    private final Long id;
    private final String code;
    private final String name;
    private final String description;
    private final TaskType type;
    private final TaskPriority priority;
    private final Project project;
    private final User author;
    private final User executor;
    private final ZonedDateTime created;
    private final ZonedDateTime updated;
    private final WorkFlowStatus status;
    private final Duration estimation;
    private final Duration timeSpent;
    private final Duration timeLeft;
    private final List<RelatedTask> relatedTasks;
    private final List<TaskAttachment> attachments;
    private final List<WorkLog> workLogs;

    public Task(Long id, String code, String name, String description,
                TaskType type, TaskPriority priority, Project project,
                User author, User executor,
                ZonedDateTime created, ZonedDateTime updated,
                WorkFlowStatus status,
                Duration estimation, Duration timeSpent, Duration timeLeft,
                List<RelatedTask> relatedTasks, List<TaskAttachment> attachments, List<WorkLog> workLogs) {
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
        this.estimation = estimation;
        this.timeSpent = timeSpent;
        this.timeLeft = timeLeft;
        this.relatedTasks = relatedTasks;
        this.attachments = attachments;
        this.workLogs = workLogs;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskType getType() {
        return type;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public Project getProject() {
        return project;
    }

    public User getAuthor() {
        return author;
    }

    public User getExecutor() {
        return executor;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public WorkFlowStatus getStatus() {
        return status;
    }

    public Duration getEstimation() {
        return estimation;
    }

    public Duration getTimeSpent() {
        return timeSpent;
    }

    public Duration getTimeLeft() {
        return timeLeft;
    }

    public List<RelatedTask> getRelatedTasks() {
        return relatedTasks;
    }

    public List<TaskAttachment> getAttachments() {
        return attachments;
    }

    public List<WorkLog> getWorkLogs() {
        return workLogs;
    }
}
