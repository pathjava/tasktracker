package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Объект, содержащий краткие данные о связанной задаче и выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class RelatedTaskDtoPreview {

    @NotNull
    private Long id;

    @NotNull
    private RelationTypeDtoPreview relationType;

    @NotNull
    private Long currentTaskId;

    @NotNull
    private TaskDtoPreview attachedTask;

}
