package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;

/**
 * DtoFull для проекта
 * @author Pavel Khovaylo
 */
@Valid
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
    @Positive
    Long id;
    /**
     * имя проекта
     */
    @NotNull
    @Length(min = 1, max = 100)
    String name;
    /**
     * описание проекта
     */
    @Length(max = 800)
    String description;
    /**
     * уникальная аббревиатура, созданная на основании имени проекта
     */
    @NotNull
    @Length(min = 2, max = 10)
    String prefix;
    /**
     * владелец (создатель) проекта
     */
    @NotNull
    UserDtoPreview owner;
    /**
     * время создания проекта
     */
    ZonedDateTime created;
}