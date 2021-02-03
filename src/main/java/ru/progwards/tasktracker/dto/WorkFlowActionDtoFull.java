package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.annotation.TaskTypeNameValid;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;
import javax.validation.constraints.*;

/**
 * Действия над задачей, переводящие её из одного состояния WorkFlowStatus в другое
 *
 * @author Aleksandr Sidelnikov
 */

@Data
@AllArgsConstructor
@TaskTypeNameValid(groups = {Create.class, Update.class})
public class WorkFlowActionDtoFull {

    @Min(value = 0, groups = Update.class)
//    @Max(value = Long.MAX_VALUE, groups = Update.class)
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    /**
     * Родительский статус
     *
     * Статус задач, к которым применимо данное действие
     */
    private Long parentStatus_id;

    /**
     * Наименование действия
     */
    @NotBlank(groups = {Create.class, Update.class})
    private String name;

    /**
     * Идентификатор статуса, в который переводится задача после применения действия
     */
    private Long nextStatus_id;

}
