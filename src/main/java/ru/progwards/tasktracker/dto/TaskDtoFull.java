package ru.progwards.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Объект, содержащий полные данные задачи, выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class TaskDtoFull {

    private Long id;
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

}