package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DtoPreview для TaskPriority
 * @author Pavel Khovaylo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskPriorityDtoPreview {
    /**
     * идентификатор
     */
    Long id;
    /**
     * имя
     */
    String name;
}
