package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.verificationstage.Create;
import ru.progwards.tasktracker.util.validator.verificationstage.Update;

import javax.validation.constraints.*;

/**
 * Объект, содержащий полные данные об отношениях связанных задач
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class RelationTypeDtoFull {

    @Min(value = 0, groups = Update.class)
    @Max(value = Long.MAX_VALUE, groups = Update.class)
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotEmpty(groups = {Create.class, Update.class})
    @Size(min = 1, max = 15, groups = {Create.class, Update.class})
    private String name;

    private Long counterRelationId;

}
