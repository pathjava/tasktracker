package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.util.validator.annotation.EmailValid;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Aleksandr Sidelnikov
 */
@Data
@AllArgsConstructor
public class UserDtoFull {

    private Long id;
    private String name;
    @NotEmpty
    @Email
    @EmailValid
    private String email;
    private String password;
    private List<UserRoleDtoPreview> roles;

}