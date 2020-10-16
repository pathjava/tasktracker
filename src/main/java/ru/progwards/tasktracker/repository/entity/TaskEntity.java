package ru.progwards.tasktracker.repository.entity;

import ru.progwards.tasktracker.service.vo.*;
import ru.progwards.tasktracker.util.types.TaskPriority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkFlowStatus;

import java.util.List;

public class TaskEntity {

    private final Long id;
    private final String code;
    private final String name;
    private final String description;
    private final TaskType type;
    private final TaskPriority priority;
    private final Project project;
    private final User author;
    private final User executor;
    private final Long created;
    private final Long updated;
    private final WorkFlowStatus status;
    private final Long estimation;
    private final Long timeSpent;
    private final Long timeLeft;
    private final List<RelatedTask> relatedTasks;
    private final List<TaskAttachment> attachments;
    private final List<WorkLog> workLogs;

    public TaskEntity(Long id, String code, String name, String description,
                      TaskType type, TaskPriority priority, Project project,
                      User author, User executor,
                      Long created, Long updated,
                      WorkFlowStatus status,
                      Long estimation, Long timeSpent, Long timeLeft,
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

    public Long getCreated() {
        return created;
    }

    public Long getUpdated() {
        return updated;
    }

    public WorkFlowStatus getStatus() {
        return status;
    }

    public Long getEstimation() {
        return estimation;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public Long getTimeLeft() {
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
