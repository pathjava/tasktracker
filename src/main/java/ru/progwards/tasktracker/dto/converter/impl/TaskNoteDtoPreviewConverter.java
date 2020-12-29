package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.TaskNoteDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.TaskNote;
import ru.progwards.tasktracker.service.GetService;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskNoteDtoPreviewConverter implements Converter<TaskNote, TaskNoteDtoPreview> {

    private final @NonNull GetService<Long, TaskNote> taskNoteGetService;
    @Override
    public TaskNote toModel(TaskNoteDtoPreview dto) {
        if (dto == null)
            return null;
        else {
            TaskNote taskNote = taskNoteGetService.get(dto.getId());
            taskNote.setAuthor(dto.getAuthor());
            taskNote.setUpdater(dto.getUpdater());
            taskNote.setComment(dto.getComment());
            return taskNote;
        }
    }

    @Override
    public TaskNoteDtoPreview toDto(TaskNote model) {
        if (model == null)
            return null;
        else
            return new TaskNoteDtoPreview(
                    model.getId(),
                    model.getAuthor(),
                    model.getUpdater(),
                    model.getComment()
            );
    }
}
