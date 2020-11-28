package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskNoteDtoFull;
import ru.progwards.tasktracker.service.vo.TaskNote;

/**
 * Конвертер из dto-объекта в бизнес-модель и обратно
 * @author Konstantin Kishkin
 */
@Component
public class TaskNoteDtoFullConverter implements Converter<TaskNote, TaskNoteDtoFull> {
    /**
     * метод конвертирует объект TaskNoteDto в объект TaskNote
     * @param dto объект TaskNoteDto, который конвертируется в модель
     * @return бизнес-модель TaskNote
     */
    @Override
    public TaskNote toModel(TaskNoteDtoFull dto) {
        return null;
    }

    /**
     * метод конвертирует бизнес-модель TaskNote в объект TaskNoteDto
     * @param model бизнес-модель TaskNote, которая конвертируется в TaskNoteDto
     * @return объект TaskNoteDto
     */
    @Override
    public TaskNoteDtoFull toDto(TaskNote model) {
        return null;
    }
}
