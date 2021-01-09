package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * DtoPreview для TaskPriority
 * @author Pavel Khovaylo
 */
@Valid
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@GroupSequence({Create.class, Update.class})
public class TaskPriorityDtoPreview {
    /**
     * идентификатор
     */
    @Min(0L)
    @Max(Long.MAX_VALUE)
    Long id;
    /**
     * имя
     */
    @NotEmpty
    @Length(min = 2, max = 40)
    String name;
}