package ru.progwards.tasktracker.dto.converter.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskPriorityDtoFull;
import ru.progwards.tasktracker.model.TaskPriority;
import ru.progwards.tasktracker.service.GetService;

import java.util.ArrayList;

/**
 * Конвертер TaskPriority <-> TaskPriorityDtoFull
 * @author Pavel Khovaylo
 */
@Component
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskPriorityDtoFullConverter implements Converter<TaskPriority, TaskPriorityDtoFull> {
    /**
     * сервис для получения TaskPriority из базы данных
     */
    GetService<Long, TaskPriority> taskPriorityGetService;

    /**
     * метод конвертирует объект TaskPriorityDto в объект TaskPriority
     * @param dto объект TaskPriorityDto, который конвертируется в модель
     * @return бизнес-модель TaskPriority
     */
    @Override
    public TaskPriority toModel(TaskPriorityDtoFull dto) {
        if (dto == null) return null;

        if (dto.getId() == null)
            return new TaskPriority(dto.getId(), dto.getName(), dto.getValue(), new ArrayList<>());

        TaskPriority model = taskPriorityGetService.get(dto.getId());

        if (model != null) {
            model.setName(dto.getName());
            model.setValue(dto.getValue());
        }

        return model;
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