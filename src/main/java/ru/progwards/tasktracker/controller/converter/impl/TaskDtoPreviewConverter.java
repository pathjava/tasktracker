package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoPreview;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskDtoPreviewConverter implements Converter<Task, TaskDtoPreview> {

    @Override
    public Task toModel(TaskDtoPreview dto) {
        return null;
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return dto превью задачи для отображения в пользовательском интерфейсе
     */
    @Override
    public TaskDtoPreview toDto(Task model) {
        if (model == null)
            return null;
        else
            return new TaskDtoPreview(
                    model.getId(),
                    model.getCode(),
                    model.getName()
            );
    }
}
