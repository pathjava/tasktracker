package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.progwards.tasktracker.util.validator.annotation.UniquePrefix;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DtoPreview для проекта
 * @author Pavel Khovaylo
 */
@Valid
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@GroupSequence({Create.class, Update.class})
public class ProjectDtoPreview {
    /**
     * идентификатор проекта
     */
//    @NotNull
    @Min(0L)
    @Max(Long.MAX_VALUE)
    Long id;
    /**
     * имя проекта
     */
    @NotEmpty
    @Size(min = 3, max = 100)
    String name;
}