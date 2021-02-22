package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Объект, содержащий краткие данные задачи для отображения в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class TaskDtoPreview {

    @Positive
    @NotNull
    private Long id;

    private String code;

    private String name;

    private TaskTypeDtoPreview type;

    private TaskPriorityDtoPreview priority;

}
