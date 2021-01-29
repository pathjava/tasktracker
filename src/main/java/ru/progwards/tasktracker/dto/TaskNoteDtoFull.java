package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.model.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * DtoFull для TaskNote
 *
 * @author Konstantin Kishkin
 */

@Data
@AllArgsConstructor
public class TaskNoteDtoFull {

    @Min(0)
    @Max(Long.MAX_VALUE)
    @NotNull
    private Long id;

    @NotBlank
    private User author;

    private User updater;

    @NotNull
    private ZonedDateTime created;

    private ZonedDateTime updated;


    private String comment;

    @NotNull
    private TaskDtoFull task;

    @NotNull
    private UserDtoPreview user;
}
