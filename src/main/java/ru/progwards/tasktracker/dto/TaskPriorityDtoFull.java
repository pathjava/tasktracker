package ru.progwards.tasktracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * DtoFull для TaskPriority
 * @author Pavel Khovaylo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskPriorityDtoFull {
    /**
     * идентификатор
     */
    @Positive
    Long id;
    /**
     * имя
     */
    @NotNull
    @Length(min = 1, max = 40)
    String name;
    /**
     * числовой приоритет
     */
    Integer value;
}