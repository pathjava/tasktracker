package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * Объект, содержащий краткие данные задачи для отображения в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class TaskDtoPreview {

    @Min(0)
    @Max(Long.MAX_VALUE)
    @NotNull
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 10)
    private String code;

    @NotEmpty
    private String name;

    @NotNull
    private TaskTypeDtoPreview type;

    @NotNull
    private TaskPriorityDtoPreview priority;

}
