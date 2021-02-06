package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.*;
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
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private Long id;

    @NotBlank
    private User author;

    private User updater;

    @NotNull
    private ZonedDateTime created;

    private ZonedDateTime updated;


    private String comment;

    @NotNull
    private TaskDtoPreview task;

    @NotNull
    private UserDtoPreview user;
}
