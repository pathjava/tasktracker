package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Artem Dikov
 */
@AllArgsConstructor
@Data
public class AccessTypeDtoPreview {
    // ENUM
    @NotNull
    private String name;
}
