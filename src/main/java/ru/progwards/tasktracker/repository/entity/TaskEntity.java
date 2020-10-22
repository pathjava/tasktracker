package ru.progwards.tasktracker.repository.entity;

import ru.progwards.tasktracker.service.vo.*;
import ru.progwards.tasktracker.util.types.TaskPriority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkFlowStatus;

import java.util.List;

public class TaskEntity {

    private Long id;
    private String code;
    private String name;
    private String description;
    private TaskType type;
    private TaskPriority priority;
    private Long project_id;
    private User author;
    private User executor;
    private Long created;
    private Long updated;
    private WorkFlowStatus status;
    private Long estimation;
    private Long timeSpent;
    private Long timeLeft;
    private List<RelatedTask> relatedTasks;
    private List<TaskAttachment> attachments;
    private List<WorkLog> workLogs;
    private Boolean isDeleted;

    public TaskEntity(
            Long id, String code, String name, String description,
            TaskType type, TaskPriority priority, Long project_id,
            User author, User executor,
            Long created, Long updated,
            WorkFlowStatus status,
            Long estimation, Long timeSpent, Long timeLeft,
            List<RelatedTask> relatedTasks, List<TaskAttachment> attachments, List<WorkLog> workLogs,
            Boolean isDeleted
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.project_id = project_id;
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
        this.isDeleted = isDeleted;
    }

    public TaskEntity() {
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

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public WorkFlowStatus getStatus() {
        return status;
    }

    public void setStatus(WorkFlowStatus status) {
        this.status = status;
    }

    public Long getEstimation() {
        return estimation;
    }

    public void setEstimation(Long estimation) {
        this.estimation = estimation;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public List<RelatedTask> getRelatedTasks() {
        return relatedTasks;
    }

    public void setRelatedTasks(List<RelatedTask> relatedTasks) {
        this.relatedTasks = relatedTasks;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
