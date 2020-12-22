package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskTypeDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.TaskType;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskTypeDtoPreviewConverter implements Converter<TaskType, TaskTypeDtoPreview> {

    @Autowired
    private GetService<Long, TaskType> taskTypeGetService;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public TaskType toModel(TaskTypeDtoPreview dto) {
        if (dto == null)
            return null;
        else {
            TaskType taskType = taskTypeGetService.get(dto.getId());
            return new TaskType(
                    dto.getId(),
                    taskType.getProject(),
                    taskType.getWorkFlow(),
                    dto.getName(),
                    taskType.getTasks()
            );
        }
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public TaskTypeDtoPreview toDto(TaskType model) {
        if (model == null)
            return null;
        else
            return new TaskTypeDtoPreview(
                    model.getId(),
                    model.getName()
            );
    }
}
