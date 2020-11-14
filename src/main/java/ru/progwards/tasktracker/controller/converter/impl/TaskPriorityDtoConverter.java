package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskPriorityDto;
import ru.progwards.tasktracker.service.vo.TaskPriority;

/**
 * Конвертер из dto-объекта в бизнес-модель и обратно
 * @author Pavel Khovaylo
 */
@Component
public class TaskPriorityDtoConverter implements Converter<TaskPriority, TaskPriorityDto> {

    /**
     * метод конвертирует объект TaskPriorityDto в объект TaskPriority
     * @param dto объект TaskPriorityDto, который конвертируется в модель
     * @return бизнес-модель TaskPriority
     */
    @Override
    public TaskPriority toModel(TaskPriorityDto dto) {
        return new TaskPriority(dto.getId(), dto.getName(), dto.getValue());
    }

    /**
     * метод конвертирует бизнес-модель TaskPriority в объект TaskPriorityDto
     * @param model бизнес-модель TaskPriority, которая конвертируется в TaskPriorityDto
     * @return объект TaskPriorityDto
     */
    @Override
    public TaskPriorityDto toDto(TaskPriority model) {
        return new TaskPriorityDto(model.getId(), model.getName(), model.getValue());
    }
}