package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.model.types.WorkFlowState;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * Статус, в который может быть переведена задача по ходу WorkFlowEntity
 *
 * @author Gregory Lobkov
 */
@Data
@AllArgsConstructor
public class WorkFlowStatusDtoFull {

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    Long id;

    /**
     * Родительский WF
     */
    @NotNull(groups = {Create.class, Update.class})
    Long workflow_id;

    /**
     * Наименование
     */
    @NotEmpty(groups = {Create.class, Update.class})
    String name;

    /**
     * Состояние задачи
     */
    @NotNull(groups = {Create.class, Update.class})
    WorkFlowState state;

    /**
     * На данный статус задачу можно переводить из любого состояния
     */
    @NotNull(groups = {Create.class, Update.class})
    Boolean alwaysAllow;

    /**
     * Действия, которые могут быть применены к задаче с данным статусом
     */
    List<WorkFlowActionDtoPreview> actions;

}
