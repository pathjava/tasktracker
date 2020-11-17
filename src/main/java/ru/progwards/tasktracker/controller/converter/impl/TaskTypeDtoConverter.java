package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskTypeDto;
import ru.progwards.tasktracker.service.vo.TaskType;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskTypeDtoConverter implements Converter<TaskType, TaskTypeDto> {

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public TaskType toModel(TaskTypeDto dto) {
        if (dto == null)
            return null;
        else
            return new TaskType(
                    dto.getId(),
                    dto.getProject_id(),
                    dto.getWorkFlow_id(),
                    dto.getName()
            );
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public TaskTypeDto toDto(TaskType model) {
        if (model == null)
            return null;
        else
            return new TaskTypeDto(
                    model.getId(),
                    model.getProject_id(),
                    model.getWorkFlow_id(),
                    model.getName()
            );
    }
}
