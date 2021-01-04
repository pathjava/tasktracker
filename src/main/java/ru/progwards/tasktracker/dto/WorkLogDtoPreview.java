package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Объект, содержащий краткие лог-данные (Журнала работ) о действиях в задаче
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class WorkLogDtoPreview {

    @Min(0)
    @Max(Long.MAX_VALUE)
    @NotNull
    private Long id;

    @NotNull
    private Duration spent;

    @NotNull
    private UserDtoPreview worker;

    @NotNull
    private ZonedDateTime start;

}
