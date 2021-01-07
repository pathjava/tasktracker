package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.progwards.tasktracker.util.validator.annotation.UniquePrefix;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.ZonedDateTime;

/**
 * DtoFull для проекта
 * @author Pavel Khovaylo
 */
@Valid
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@GroupSequence({Create.class, Update.class})
@UniquePrefix(groups = {Create.class, Update.class})
public class ProjectDtoFull {
    /**
     * идентификатор проекта
     */
    @NotNull(groups = Update.class)
    @Min(0L)
    @Max(Long.MAX_VALUE)
    Long id;
    /**
     * имя проекта
     */
    @NotEmpty
    @Size(min = 2, max = 100)
    String name;
    /**
     * описание проекта
     */
    @Size(max = 800)
    String description;
    /**
     * уникальная аббревиатура, созданная на основании имени проекта
     */
    @NotEmpty
    @Size(min = 2, max = 10)
    String prefix;
    /**
     * владелец (создатель) проекта
     */
    @NotNull
    @Valid
    UserDtoPreview owner;
    /**
     * время создания проекта
     */
    @PastOrPresent(groups = Update.class)
    ZonedDateTime created;
}