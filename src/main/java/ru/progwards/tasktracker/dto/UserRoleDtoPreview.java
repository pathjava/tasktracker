package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.model.types.SystemRole;

import javax.validation.constraints.NotNull;

/**
 * @author Artem Dikov
 */
@AllArgsConstructor
@Data
public class UserRoleDtoPreview {

    @NotNull
    private Long id;

    private String name;

    private String systemRole;

}
