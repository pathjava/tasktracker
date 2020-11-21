package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelatedTaskDto;
import ru.progwards.tasktracker.service.vo.RelatedTask;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskDtoConverter implements Converter<RelatedTask, RelatedTaskDto> {
    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public RelatedTask toModel(RelatedTaskDto dto) {
        if (dto == null)
            return null;
        else
            return new RelatedTask(
                    dto.getId(),
                    dto.getRelationType(),
                    dto.getCurrentTaskId(),
                    dto.getAttachedTaskId()
            );
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public RelatedTaskDto toDto(RelatedTask model) {
        if (model == null)
            return null;
        else
            return new RelatedTaskDto(
                    model.getId(),
                    model.getRelationType(),
                    model.getCurrentTaskId(),
                    model.getAttachedTaskId()
            );
    }
}
