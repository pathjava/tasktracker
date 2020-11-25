package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.service.vo.TaskType;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskTypeDtoFullConverter implements Converter<TaskType, TaskTypeDtoFull> {

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public TaskType toModel(TaskTypeDtoFull dto) {
        if (dto == null)
            return null;
        else
            return new TaskType(
                    dto.getId(),
                    dto.getWorkFlow(),
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
    public TaskTypeDtoFull toDto(TaskType model) {
        if (model == null)
            return null;
        else
            return new TaskTypeDtoFull(
                    model.getId(),
                    model.getWorkFlow(),
                    model.getName()
            );
    }
}
