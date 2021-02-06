package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.annotation.WorkFlowActionNameValid;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Действия над задачей, переводящие её из одного состояния WorkFlowStatus в другое
 *
 * @author Aleksandr Sidelnikov
 */

@Data
@AllArgsConstructor
@WorkFlowActionNameValid(groups = {Create.class, Update.class})
public class WorkFlowActionDtoFull {

    @Min(value = 0, groups = Update.class)
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    /**
     * Родительский статус
     * <p>
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
