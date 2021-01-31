package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Объект, содержащий полные лог-данные (Журнала работ) о действиях в задаче
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class WorkLogDtoFull {

    @Min(value = 0, groups = Update.class)
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotNull(groups = {Create.class, Update.class})
    private TaskDtoPreview task;

    @NotNull(groups = {Create.class, Update.class})
    private Duration spent;

    @NotNull(groups = {Create.class, Update.class})
    private UserDtoPreview worker;

    @NotNull(groups = {Create.class, Update.class})
    private ZonedDateTime start;

    @NotBlank(groups = {Create.class, Update.class})
    private String description;

    @NotBlank(groups = {Create.class, Update.class})
    private String estimateChange;

    private Duration estimateValue;

}
