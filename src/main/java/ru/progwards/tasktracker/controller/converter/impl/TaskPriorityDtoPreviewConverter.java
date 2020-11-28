package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskPriorityDtoPreview;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.TaskPriority;

/**
 * Конвертер TaskPriority <-> TaskPriorityDtoPreview
 * @author Pavel Khovaylo
 */
@Component
public class TaskPriorityDtoPreviewConverter implements Converter<TaskPriority, TaskPriorityDtoPreview> {
    /**
     * сервис для получения бизнес модели TaskPriority
     */
    @Autowired
    private GetService<Long, TaskPriority> taskPriorityGetService;

    /**
     * метод конвертирует объект TaskPriorityDtoPreview в объект TaskPriority
     * @param dto объект TaskPriorityDtoPreview, который конвертируется в модель
     * @return бизнес-модель TaskPriority
     */
    @Override
    public TaskPriority toModel(TaskPriorityDtoPreview dto) {
        if (dto == null)
            return null;

        TaskPriority model = taskPriorityGetService.get(dto.getId());

        if (model != null)
            return new TaskPriority(dto.getId(), dto.getName(), model.getValue());

        return null;
    }

    /**
     * метод конвертирует бизнес-модель TaskPriority в объект TaskPriorityDtoPreview
     * @param model бизнес-модель TaskPriority, которая конвертируется в TaskPriorityDtoPreview
     * @return объект TaskPriorityDtoPreview
     */
    @Override
    public TaskPriorityDtoPreview toDto(TaskPriority model) {
        return new TaskPriorityDtoPreview(model.getId(), model.getName());
    }
}