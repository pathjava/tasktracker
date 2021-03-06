package ru.progwards.tasktracker.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.progwards.tasktracker.util.validator.annotation.CorrectPrefix;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

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
//@GroupSequence({Create.class, Update.class})
@CorrectPrefix(groups = {Create.class, Update.class})
public class ProjectDtoFull {
    /**
     * идентификатор проекта
     */
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    @Min(0L)
    Long id;
    /**
     * имя проекта
     */
    @NotEmpty(groups = {Create.class, Update.class})
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
    @NotEmpty(groups = {Create.class, Update.class})
    @Size(min = 2, max = 10)
    String prefix;
    /**
     * владелец (создатель) проекта
     */
    @NotNull(groups = {Create.class, Update.class})
    @Valid
    UserDtoPreview owner;
    /**
     * время создания проекта
     */
    @PastOrPresent(groups = Update.class)
    ZonedDateTime created;
}