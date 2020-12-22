package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskNoteDtoPreview;
import ru.progwards.tasktracker.model.TaskNote;

/**
 * Конвертер из dto-объекта в бизнес-модель и обратно
 * @author Konstantin Kishkin
 */
@Component
public class TaskNoteDtoFullConverter implements Converter<TaskNote, TaskNoteDtoPreview> {
    /**
     * метод конвертирует объект TaskNoteDto в объект TaskNote
     * @param dto объект TaskNoteDto, который конвертируется в модель
     * @return бизнес-модель TaskNote
     */
    @Override
    public TaskNote toModel(TaskNoteDtoPreview dto) {
        return null;
    }

    /**
     * метод конвертирует бизнес-модель TaskNote в объект TaskNoteDto
     * @param model бизнес-модель TaskNote, которая конвертируется в TaskNoteDto
     * @return объект TaskNoteDto
     */
    @Override
    public TaskNoteDtoPreview toDto(TaskNote model) {
        return null;
    }
}
