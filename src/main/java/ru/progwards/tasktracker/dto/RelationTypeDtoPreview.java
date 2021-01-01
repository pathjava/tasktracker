package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Объект, содержащий краткие данные об отношениях связанных задач
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class RelationTypeDtoPreview {

    @NotNull
    private Long id;

    @NotEmpty
    private String name;

}
