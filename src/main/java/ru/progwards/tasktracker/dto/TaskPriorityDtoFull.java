package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DtoFull для TaskPriority
 * @author Pavel Khovaylo
 */
@Valid
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@GroupSequence({Create.class, Update.class})
public class TaskPriorityDtoFull {
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
    /**
     * числовой приоритет
     */
    @NotNull
    @Min(0)
    @Max(Integer.MAX_VALUE)
    Integer value;
}