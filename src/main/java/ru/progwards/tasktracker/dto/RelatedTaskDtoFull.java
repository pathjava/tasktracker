package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Объект, содержащий полные данные о связанной задаче и выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class RelatedTaskDtoFull {

    private Long id;
    private RelationTypeDtoPreview relationType;
    private Long currentTaskId;
    private TaskDtoPreview attachedTask;

}
