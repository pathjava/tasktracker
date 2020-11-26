package ru.progwards.tasktracker.repository.entity;

import java.util.List;
import java.util.Random;

/**
 * Сущность для хранения задачи в БД
 *
 * @author Oleg Kiselev
 */
public class TaskEntity {

    private Long id;
    private String code;
    private String name;
    private String description;
    private TaskTypeEntity type;
    private TaskPriorityEntity priority;
    private ProjectEntity project;
    private UserEntity author;
    private UserEntity executor;
    private Long created;
    private Long updated;
    private WorkFlowStatusEntity status;
    private List<WorkFlowActionEntity> actions;
    private Long estimation;
    private Long timeSpent;
    private Long timeLeft;
    private List<RelatedTaskEntity> relatedTasks;
    private List<TaskAttachmentEntity> attachments;
    private List<WorkLogEntity> workLogs;
    private Boolean isDeleted;

    public TaskEntity() {
    }

    public TaskEntity(
            Long id, String code, String name, String description,
            TaskTypeEntity type, TaskPriorityEntity priority, ProjectEntity project,
            UserEntity author, UserEntity executor,
            Long created, Long updated,
            WorkFlowStatusEntity status, List<WorkFlowActionEntity> actions,
            Long estimation, Long timeSpent, Long timeLeft,
            List<RelatedTaskEntity> relatedTasks, List<TaskAttachmentEntity> attachments,
            List<WorkLogEntity> workLogs, Boolean isDeleted
    ) {
        if (id == null) //TODO - for testing generate id
            id = new Random().nextLong();
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
        this.attachments = attachments;
        this.workLogs = workLogs;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
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

    public TaskTypeEntity getType() {
        return type;
    }

    public void setType(TaskTypeEntity type) {
        this.type = type;
    }

    public TaskPriorityEntity getPriority() {
        return priority;
    }

    public void setPriority(TaskPriorityEntity priority) {
        this.priority = priority;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public UserEntity getExecutor() {
        return executor;
    }

    public void setExecutor(UserEntity executor) {
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

    public WorkFlowStatusEntity getStatus() {
        return status;
    }

    public void setStatus(WorkFlowStatusEntity status) {
        this.status = status;
    }

    public List<WorkFlowActionEntity> getActions() {
        return actions;
    }

    public void setActions(List<WorkFlowActionEntity> actions) {
        this.actions = actions;
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

    public List<RelatedTaskEntity> getRelatedTasks() {
        return relatedTasks;
    }

    public void setRelatedTasks(List<RelatedTaskEntity> relatedTasks) {
        this.relatedTasks = relatedTasks;
    }

    public List<TaskAttachmentEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TaskAttachmentEntity> attachments) {
        this.attachments = attachments;
    }

    public List<WorkLogEntity> getWorkLogs() {
        return workLogs;
    }

    public void setWorkLogs(List<WorkLogEntity> workLogs) {
        this.workLogs = workLogs;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
