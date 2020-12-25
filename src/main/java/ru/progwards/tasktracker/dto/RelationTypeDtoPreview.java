package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Объект, содержащий краткие данные об отношениях связанных задач
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
public class RelationTypeDtoPreview {

    private Long id;
    private String name;

}
