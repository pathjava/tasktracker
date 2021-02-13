package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.annotation.EmailValid;
import ru.progwards.tasktracker.util.validator.annotation.PasswordValid;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author Aleksandr Sidelnikov
 */
@Data
@AllArgsConstructor
@EmailValid(groups = {Create.class, Update.class})
@PasswordValid(groups = {Create.class, Update.class})
public class UserDtoFull {

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotBlank(groups = {Create.class, Update.class})
    private String name;

    @NotBlank(groups = {Create.class, Update.class})
    @Email(groups = {Create.class, Update.class})
    private String email;

    @NotBlank(groups = {Create.class, Update.class})
    private String password;

    @NotBlank(groups = {Create.class, Update.class})
    transient private String passwordConfirm;

    private List<UserRoleDtoPreview> roles;

}