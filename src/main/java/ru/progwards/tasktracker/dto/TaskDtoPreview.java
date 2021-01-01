package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Объект, содержащий краткие данные задачи для отображения в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class TaskDtoPreview {

    @NotNull
    private Long id;

    @NotEmpty
    private String code;

    @NotEmpty
    private String name;

    @NotNull
    private TaskTypeDtoPreview type;

    @NotNull
    private TaskPriorityDtoPreview priority;

}
