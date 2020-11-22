package ru.progwards.tasktracker.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.progwards.tasktracker.service.vo.*;
import ru.progwards.tasktracker.service.vo.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Объект, содержащий данные задачи, выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class TaskDtoFull {

    private final Long id;
    private String code;
    private String name;
    private String description;
    private TaskTypeDto type;
    private TaskPriorityDto priority;
    private Long project_id;
    private UserDto author;
    private UserDto executor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime updated;
    private WorkFlowStatusDto status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS")
    private Duration estimation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS")
    private Duration timeSpent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS")
    private Duration timeLeft;
    private List<RelatedTaskDto> relatedTasks;
    private List<TaskAttachmentDto> attachments;
    private List<WorkLogDto> workLogs;

    public TaskDtoFull(
            Long id, String code, String name, String description,
            TaskTypeDto type, TaskPriorityDto priority, Long project_id,
            UserDto author, UserDto executor,
            ZonedDateTime created, ZonedDateTime updated,
            WorkFlowStatusDto status,
            Duration estimation, Duration timeSpent, Duration timeLeft,
            List<RelatedTaskDto> relatedTasks, List<TaskAttachmentDto> attachments, List<WorkLogDto> workLogs
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

    public TaskTypeDto getType() {
        return type;
    }

    public void setType(TaskTypeDto type) {
        this.type = type;
    }

    public TaskPriorityDto getPriority() {
        return priority;
    }

    public void setPriority(TaskPriorityDto priority) {
        this.priority = priority;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public UserDto getExecutor() {
        return executor;
    }

    public void setExecutor(UserDto executor) {
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

    public WorkFlowStatusDto getStatus() {
        return status;
    }

    public void setStatus(WorkFlowStatusDto status) {
        this.status = status;
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

    public List<RelatedTaskDto> getRelatedTasks() {
        return relatedTasks;
    }

    public void setRelatedTasks(List<RelatedTaskDto> relatedTasks) {
        this.relatedTasks = relatedTasks;
    }

    public List<TaskAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TaskAttachmentDto> attachments) {
        this.attachments = attachments;
    }

    public List<WorkLogDto> getWorkLogs() {
        return workLogs;
    }

    public void setWorkLogs(List<WorkLogDto> workLogs) {
        this.workLogs = workLogs;
    }
}
