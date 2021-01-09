package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.model.User;

/**
 * DtoPreview для TaskNote
 *
 * @author Konstantin Kishkin
 */

@Data
@AllArgsConstructor
public class TaskNoteDtoPreview {

    private Long id;
    private User author;
    private User updater;
    private String comment;

}