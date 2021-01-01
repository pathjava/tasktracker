package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.annotation.EmailValid;
import ru.progwards.tasktracker.util.validator.verificationstage.Create;
import ru.progwards.tasktracker.util.validator.verificationstage.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * @author Aleksandr Sidelnikov
 */
@Data
@AllArgsConstructor
@EmailValid(groups = {Create.class, Update.class})
public class UserDtoFull {

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotEmpty(groups = {Create.class, Update.class})
    private String name;

    @NotEmpty(groups = {Create.class, Update.class})
    @Email(groups = {Create.class, Update.class})
    private String email;

    @NotEmpty(groups = {Create.class, Update.class})
    private String password;

    private List<UserRoleDtoPreview> roles;

}