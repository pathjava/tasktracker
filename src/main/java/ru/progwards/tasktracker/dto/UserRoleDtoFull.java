package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.progwards.tasktracker.model.types.SystemRole;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * @author Artem Dikov, Konstantin Kishkin
 */
@AllArgsConstructor
@Data
public class UserRoleDtoFull {

    @Min(0)
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private String systemRole;
    //private List<AccessRuleDtoFull> accessRules; //если раскоментить, то будет циклическая зависимость
}
