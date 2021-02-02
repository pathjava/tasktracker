package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.progwards.tasktracker.model.types.SystemRole;

import javax.validation.constraints.NotNull;

/**
 * Статус авторизации пользователя
 *
 * @author Artem Dikov, Konstantin Kishkin
 */
@AllArgsConstructor
@Data
public class UserRoleDtoPreview {

    @NotNull
    private Long id;

    SystemRole role;
}
