package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.model.types.SystemRole;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * Статус пользователя
 *
 * @author Artem Dikov, Konstantin Kishkin
 */
@AllArgsConstructor
@Data
public class UserRoleDtoFull {

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotNull(groups = {Create.class, Update.class})
    private SystemRole systemRole;

    private String name;
    private List<Long> accessRules;
    private List<UserDtoPreview> userDtoPreviewList;
}
