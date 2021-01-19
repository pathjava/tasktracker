package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.dto.TaskPriorityDtoPreview;
import ru.progwards.tasktracker.dto.TaskTypeDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskPriority;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.service.GetService;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskDtoPreviewConverter implements Converter<Task, TaskDtoPreview> {

    private final GetService<Long, Task> taskGetService;
    private final Converter<TaskType, TaskTypeDtoPreview> taskTypeDtoConverter;
    private final Converter<TaskPriority, TaskPriorityDtoPreview> taskPriorityDtoConverter;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public Task toModel(TaskDtoPreview dto) {
        return taskGetService.get(dto.getId());
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return dto превью задачи для отображения в пользовательском интерфейсе
     */
    @Override
    public TaskDtoPreview toDto(Task model) {
        return new TaskDtoPreview(
                model.getId(),
                model.getCode(),
                model.getName(),
                taskTypeDtoConverter.toDto(model.getType()),
                checkTaskPriorityDto(model.getPriority())
        );
    }

    /**
     * Метод проверки параметра TaskPriority на null
     *
     * @param priority TaskPriority
     * @return TaskPriorityDtoPreview
     */
    private TaskPriorityDtoPreview checkTaskPriorityDto(TaskPriority priority) {
        return priority != null ? taskPriorityDtoConverter.toDto(priority) : null;
    }
}
