package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Aleksandr Sidelnikov
 */
@Data
@AllArgsConstructor
public class UserDtoPreview {

    @NotNull
    private Long id;

    @NotEmpty
    private String name;

}
