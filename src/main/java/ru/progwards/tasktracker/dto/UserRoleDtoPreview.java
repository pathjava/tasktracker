package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author Artem Dikov
 */
@AllArgsConstructor
@Data
public class UserRoleDtoPreview {
    @EqualsAndHashCode.Exclude
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;
    @NotBlank
    private String name;
}
