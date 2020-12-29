package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DtoPreview для проекта
 * @author Pavel Khovaylo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDtoPreview {
    /**
     * идентификатор проекта
     */
    Long id;
    /**
     * имя проекта
     */
    String name;
}