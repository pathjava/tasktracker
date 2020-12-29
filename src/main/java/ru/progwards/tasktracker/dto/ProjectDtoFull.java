package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

/**
 * DtoFull для проекта
 * @author Pavel Khovaylo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDtoFull {
    /**
     * идентификатор проекта
     */
    Long id;
    /**
     * имя проекта
     */
    String name;
    /**
     * описание проекта
     */
    String description;
    /**
     * уникальная аббревиатура, созданная на основании имени проекта
     */
    String prefix;
    /**
     * владелец (создатель) проекта
     */
    UserDtoPreview owner;
    /**
     * время создания проекта
     */
    ZonedDateTime created;
}