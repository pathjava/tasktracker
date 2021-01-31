package ru.progwards.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.*;
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

    @Min(value = 0, groups = Update.class)
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotBlank(groups = Update.class)
    @Size(min = 1, max = 10, groups = Update.class)
    @Null(groups = Create.class)
    private String code;

    @NotBlank(groups = {Create.class, Update.class})
    private String name;

    private String description;

    @NotNull(groups = {Create.class, Update.class})
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

    @Null(groups = Create.class)
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
