package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Artem Dikov
 */
@AllArgsConstructor
@Data
public class UserRoleDtoPreview {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
}
