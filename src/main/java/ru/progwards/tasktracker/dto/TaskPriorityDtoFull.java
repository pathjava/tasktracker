package ru.progwards.tasktracker.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

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
//@GroupSequence({Create.class, Update.class})
public class TaskPriorityDtoFull {
    /**
     * идентификатор
     */
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    @Min(0L)
    Long id;
    /**
     * имя
     */
    @NotEmpty(groups = {Create.class, Update.class})
    @Length(min = 2, max = 40)
    String name;
    /**
     * числовой приоритет
     */
    @NotNull(groups = {Create.class, Update.class})
    @Min(0)
    @Max(Integer.MAX_VALUE)
    Integer value;
}