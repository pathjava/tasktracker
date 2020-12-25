package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

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

    private Long id;
    private Long taskId;
    private Duration spent;
    private UserDtoPreview worker;
    private ZonedDateTime when;
    private String description;
    private String estimateChange;
    private Duration estimateValue;

}
