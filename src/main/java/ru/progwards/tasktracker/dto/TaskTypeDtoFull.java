package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Объект, содержащий полные данные о типе задачи, выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class TaskTypeDtoFull {

    private final Long id;
    private ProjectDtoPreview project;
    private WorkFlowDtoPreview workFlow;
    private String name;

}
