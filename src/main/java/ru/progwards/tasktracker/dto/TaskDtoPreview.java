package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Объект, содержащий краткие данные задачи для отображения в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class TaskDtoPreview {

    @Min(0)
    @NotNull
    private Long id;

    private String code;

    private String name;

    private TaskTypeDtoPreview type;

    private TaskPriorityDtoPreview priority;

}
