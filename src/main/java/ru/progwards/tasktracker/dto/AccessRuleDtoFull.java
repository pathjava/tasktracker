package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.progwards.tasktracker.model.types.AccessObject;
import ru.progwards.tasktracker.model.types.AccessType;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author Artem Dikov, Konstantin Kishkin
 */
@AllArgsConstructor
@Data
public class AccessRuleDtoFull {

    @Min(0)
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotNull
    private UserRoleDtoPreview userRole;

    @NotNull
    AccessObject object;

    private String propertyName; // null == all

    private Long objectId; // null == all

    @NotNull
    private AccessType accessType;
}
