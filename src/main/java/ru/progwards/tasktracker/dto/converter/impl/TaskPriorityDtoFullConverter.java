package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskPriorityDtoFull;
import ru.progwards.tasktracker.model.TaskPriority;

/**
 * Конвертер TaskPriority <-> TaskPriorityDtoFull
 * @author Pavel Khovaylo
 */
@Component
public class TaskPriorityDtoFullConverter implements Converter<TaskPriority, TaskPriorityDtoFull> {

    /**
     * метод конвертирует объект TaskPriorityDto в объект TaskPriority
     * @param dto объект TaskPriorityDto, который конвертируется в модель
     * @return бизнес-модель TaskPriority
     */
    @Override
    public TaskPriority toModel(TaskPriorityDtoFull dto) {
        return new TaskPriority(dto.getId(), dto.getName(), dto.getValue(), null);
    }

    /**
     * метод конвертирует бизнес-модель TaskPriority в объект TaskPriorityDto
     * @param model бизнес-модель TaskPriority, которая конвертируется в TaskPriorityDto
     * @return объект TaskPriorityDto
     */
    @Override
    public TaskPriorityDtoFull toDto(TaskPriority model) {
        return new TaskPriorityDtoFull(model.getId(), model.getName(), model.getValue());
    }
}