package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.TaskDtoFull;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.dto.TaskNoteDtoFull;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskNote;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.service.GetService;

/**
 * Конвертер из dto-объекта в бизнес-модель и обратно
 * @author Konstantin Kishkin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskNoteDtoFullConverter implements Converter<TaskNote, TaskNoteDtoFull> {

    private final @NonNull GetService<Long, TaskNote> taskNoteGetService;
    private final @NonNull Converter<Task, TaskDtoPreview> taskDtoPreviewConverter;
    private final @NonNull Converter<User, UserDtoPreview> userDtoPreviewConverter;

    /**
     * метод конвертирует объект TaskNoteDto в объект TaskNote
     * @param dto объект TaskNoteDto, который конвертируется в модель
     * @return бизнес-модель TaskNote
     */
    @Override
    public TaskNote toModel(TaskNoteDtoFull dto) {
        if (dto == null)
            return null;
        else {
            TaskNote taskNote = taskNoteGetService.get(dto.getId());
            taskNote.setTask(taskDtoPreviewConverter.toModel(dto.getTask()));
            taskNote.setAuthor(userDtoPreviewConverter.toModel(dto.getUser()));
            taskNote.setUpdater(userDtoPreviewConverter.toModel(dto.getUser()));
            return taskNote;
        }
    }

    /**
     * метод конвертирует бизнес-модель TaskNote в объект TaskNoteDto
     * @param model бизнес-модель TaskNote, которая конвертируется в TaskNoteDto
     * @return объект TaskNoteDto
     */
    @Override
    public TaskNoteDtoFull toDto(TaskNote model) {
        if (model == null)
            return null;
        else{
            return new TaskNoteDtoFull(
                    model.getId(),
                    model.getAuthor(),
                    model.getUpdater(),
                    model.getCreated(),
                    model.getUpdated(),
                    model.getComment(),
                    taskDtoPreviewConverter.toDto(model.getTask()),
                    userDtoPreviewConverter.toDto(model.getAuthor())
            );
        }
    }
}
