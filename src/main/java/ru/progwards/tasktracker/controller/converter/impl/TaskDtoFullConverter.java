package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoFull;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskDtoFullConverter implements Converter<Task, TaskDtoFull> {
    /**
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public Task toModel(TaskDtoFull dto) {
        if (dto == null)
            return null;
        else
            return new Task(
                    checkIdNotNull(dto),
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

    private Long checkIdNotNull(TaskDtoFull dto) {
        return dto.getId() == null ? null : dto.getId();
    }

    /**
     * @param task value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public TaskDtoFull toDto(Task task) {
        if (task == null)
            return null;
        else
            return new TaskDtoFull(
                    task.getId(),
                    task.getCode(),
                    task.getName(),
                    task.getDescription(),
                    task.getType(),
                    task.getPriority(),
                    task.getProject_id(),
                    task.getAuthor(), //TODO - выводить только имя, а не объект пользователя
                    task.getExecutor(), //TODO - выводить только имя, а не объект пользователя
                    task.getCreated(),
                    task.getUpdated(),
                    task.getStatus(),
                    task.getEstimation(),
                    task.getTimeSpent(),
                    task.getTimeLeft(),
                    task.getRelatedTasks(),
                    task.getAttachments(),
                    task.getWorkLogs()
            );
    }
}
