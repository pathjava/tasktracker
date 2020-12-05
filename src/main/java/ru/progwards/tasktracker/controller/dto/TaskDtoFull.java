package ru.progwards.tasktracker.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Объект, содержащий полные данные задачи, выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class TaskDtoFull {

    private final Long id;
    private String code;
    private String name;
    private String description;
    private TaskTypeDtoPreview type;
    private TaskPriorityDtoPreview priority;
    private ProjectDtoPreview project;
    private UserDtoPreview author;
    private UserDtoPreview executor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime updated;
    private WorkFlowStatusDtoPreview status;
    private List<WorkFlowActionDtoPreview> actions;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS")
    private Duration estimation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS")
    private Duration timeSpent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS")
    private Duration timeLeft;
    private List<RelatedTaskDtoPreview> relatedTasks;
    private List<TaskAttachmentDtoPreview> attachments;

    public TaskDtoFull(
            Long id, String code, String name, String description,
            TaskTypeDtoPreview type, TaskPriorityDtoPreview priority,
            ProjectDtoPreview project, UserDtoPreview author, UserDtoPreview executor,
            ZonedDateTime created, ZonedDateTime updated,
            WorkFlowStatusDtoPreview status, List<WorkFlowActionDtoPreview> actions,
            Duration estimation, Duration timeSpent, Duration timeLeft,
            List<RelatedTaskDtoPreview> relatedTasks, List<TaskAttachmentDtoPreview> attachments
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
        this.attachments = attachments;
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

    public TaskTypeDtoPreview getType() {
        return type;
    }

    public void setType(TaskTypeDtoPreview type) {
        this.type = type;
    }

    public TaskPriorityDtoPreview getPriority() {
        return priority;
    }

    public void setPriority(TaskPriorityDtoPreview priority) {
        this.priority = priority;
    }

    public ProjectDtoPreview getProject() {
        return project;
    }

    public void setProject(ProjectDtoPreview project) {
        this.project = project;
    }

    public UserDtoPreview getAuthor() {
        return author;
    }

    public void setAuthor(UserDtoPreview author) {
        this.author = author;
    }

    public UserDtoPreview getExecutor() {
        return executor;
    }

    public void setExecutor(UserDtoPreview executor) {
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

    public WorkFlowStatusDtoPreview getStatus() {
        return status;
    }

    public void setStatus(WorkFlowStatusDtoPreview status) {
        this.status = status;
    }

    public List<WorkFlowActionDtoPreview> getActions() {
        return actions;
    }

    public void setActions(List<WorkFlowActionDtoPreview> actions) {
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

    public List<RelatedTaskDtoPreview> getRelatedTasks() {
        return relatedTasks;
    }

    public void setRelatedTasks(List<RelatedTaskDtoPreview> relatedTasks) {
        this.relatedTasks = relatedTasks;
    }

    public List<TaskAttachmentDtoPreview> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TaskAttachmentDtoPreview> attachments) {
        this.attachments = attachments;
    }
}
