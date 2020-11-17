package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoFull;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskDtoFullConverter implements Converter<Task, TaskDtoFull> {
    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public Task toModel(TaskDtoFull dto) {
        if (dto == null)
            return null;
        else
            return new Task(
                    dto.getId(),
                    dto.getCode(),
                    dto.getName(),
                    dto.getDescription(),
                    dto.getType(),
                    dto.getPriority(),
                    dto.getProject_id(),
                    dto.getAuthor(),
                    dto.getExecutor(),
                    dto.getCreated(),
                    dto.getUpdated(),
                    dto.getStatus(),
                    dto.getEstimation(),
                    dto.getTimeSpent(),
                    dto.getTimeLeft(),
                    dto.getRelatedTasks(),
                    dto.getAttachments(),
                    dto.getWorkLogs()
            );
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public TaskDtoFull toDto(Task model) {
        if (model == null)
            return null;
        else
            return new TaskDtoFull(
                    model.getId(),
                    model.getCode(),
                    model.getName(),
                    model.getDescription(),
                    model.getType(),
                    model.getPriority(),
                    model.getProject_id(),
                    model.getAuthor(), //TODO - выводить только имя, а не объект пользователя
                    model.getExecutor(), //TODO - выводить только имя, а не объект пользователя
                    model.getCreated(),
                    model.getUpdated(),
                    model.getStatus(),
                    model.getEstimation(),
                    model.getTimeSpent(),
                    model.getTimeLeft(),
                    model.getRelatedTasks(),
                    model.getAttachments(),
                    model.getWorkLogs()
            );
    }
}
