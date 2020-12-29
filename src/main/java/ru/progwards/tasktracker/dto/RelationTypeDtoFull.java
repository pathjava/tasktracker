package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Объект, содержащий полные данные об отношениях связанных задач
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class RelationTypeDtoFull {

    private Long id;
    @NotEmpty
    @Size(min = 1, max = 15)
    private String name;
    private Long counterRelationId;

}
