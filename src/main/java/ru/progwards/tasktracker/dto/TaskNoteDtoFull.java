package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.model.User;

import java.time.ZonedDateTime;

/**
 * DtoFull для TaskNote
 *
 * @author Konstantin Kishkin
 */

@Data
@AllArgsConstructor
public class TaskNoteDtoFull {

    private Long id;
    private User author;
    private User updater;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    private String comment;
    private TaskDtoFull task;
    private UserDtoPreview user;
}
