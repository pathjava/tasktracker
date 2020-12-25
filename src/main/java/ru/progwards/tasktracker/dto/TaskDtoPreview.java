package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Объект, содержащий краткие данные задачи для отображения в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class TaskDtoPreview {

    private Long id;
    private String code;
    private String name;
    private TaskTypeDtoPreview type;
    private TaskPriorityDtoPreview priority;

}
