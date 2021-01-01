package ru.progwards.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.verificationstage.Create;
import ru.progwards.tasktracker.util.validator.verificationstage.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Объект, содержащий полные данные задачи, выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class TaskDtoFull {

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotEmpty(groups = Update.class)
    @Null(groups = Create.class)
    private String code;

    @NotEmpty(groups = {Create.class, Update.class})
    private String name;

    private String description;

    private TaskTypeDtoPreview type;

    private TaskPriorityDtoPreview priority;

    @NotNull(groups = {Create.class, Update.class})
    private ProjectDtoPreview project;

    @NotNull(groups = {Create.class, Update.class})
    private UserDtoPreview author;

    private UserDtoPreview executor;

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
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
