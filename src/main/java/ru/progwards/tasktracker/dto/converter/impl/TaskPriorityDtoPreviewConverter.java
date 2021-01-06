package ru.progwards.tasktracker.dto.converter.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskPriorityDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.TaskPriority;

/**
 * Конвертер TaskPriority <-> TaskPriorityDtoPreview
 * @author Pavel Khovaylo
 */
@Component
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskPriorityDtoPreviewConverter implements Converter<TaskPriority, TaskPriorityDtoPreview> {
    /**
     * сервис для получения TaskPriority из базы данных
     */
    GetService<Long, TaskPriority> taskPriorityGetService;

    /**
     * метод конвертирует объект TaskPriorityDtoPreview в объект TaskPriority
     * @param dto объект TaskPriorityDtoPreview, который конвертируется в модель
     * @return бизнес-модель TaskPriority
     */
    @Override
    public TaskPriority toModel(TaskPriorityDtoPreview dto) {
        if (dto == null)
            return null;

        if (dto.getId() == null)
            return null;

        TaskPriority model = taskPriorityGetService.get(dto.getId());

        if (model != null)
            return new TaskPriority(dto.getId(), dto.getName(), model.getValue(), model.getTasks());

        return null;
    }

    /**
     * метод конвертирует бизнес-модель TaskPriority в объект TaskPriorityDtoPreview
     * @param model бизнес-модель TaskPriority, которая конвертируется в TaskPriorityDtoPreview
     * @return объект TaskPriorityDtoPreview
     */
    @Override
    public TaskPriorityDtoPreview toDto(TaskPriority model) {
        if (model == null)
            return null;

        return new TaskPriorityDtoPreview(model.getId(), model.getName());
    }
}