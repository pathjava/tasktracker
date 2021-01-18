package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.annotation.TaskTypeNameValid;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.*;

/**
 * Объект, содержащий полные данные о типе задачи, выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
@TaskTypeNameValid(groups = {Create.class, Update.class})
public class TaskTypeDtoFull {

    @Min(value = 0, groups = Update.class)
    @Max(value = Long.MAX_VALUE, groups = Update.class)
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    private ProjectDtoPreview project;

    private WorkFlowDtoPreview workFlow;

    @NotEmpty(groups = {Create.class, Update.class})
    private String name;

}
