package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Дерево движения задачи по статусам
 *
 * @author Gregory Lobkov
 */
@Data
@AllArgsConstructor
public class WorkFlowDtoFull {

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    Long id;

    /**
     * Наименование
     */
    @NotEmpty
    String name;

    /**
     * Признак шаблона
     *
     * Чтобы легко можно было отличить воркфлоу конкретного процесса
     * от шаблона, на основе которого будет все создаваться,
     * иначе его нельзя будет настраивать индивидуально
     */
    @NotNull
    Boolean pattern;

    /**
     * С какого статуса начинать движение задачи, идентификатор
     */
    Long start_status_id;

}
