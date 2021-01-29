package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.progwards.tasktracker.model.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * DtoPreview для TaskNote
 *
 * @author Konstantin Kishkin
 */

@Data
@AllArgsConstructor
public class TaskNoteDtoPreview {

    @Min(0)
    @Max(Long.MAX_VALUE)
    @NotNull
    private Long id;
    private User author;
    private User updater;
    private String comment;

}