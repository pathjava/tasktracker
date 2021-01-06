package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.progwards.tasktracker.util.validator.annotation.UniquePrefix;

import javax.validation.Valid;
import javax.validation.constraints.*;
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
    @Min(0L)
    @Max(Long.MAX_VALUE)
    Long id;
    /**
     * имя проекта
     */
    @NotNull
    @Size(min = 3, max = 100)
    String name;
    /**
     * описание проекта
     */
    @Size(max = 800)
    String description;
    /**
     * уникальная аббревиатура, созданная на основании имени проекта
     */
    @NotNull
    @Size(min = 2, max = 10)
    @UniquePrefix
    String prefix;
    /**
     * владелец (создатель) проекта
     */
    @NotNull
    UserDtoPreview owner;
    /**
     * время создания проекта
     */
    @PastOrPresent
    ZonedDateTime created;
}