package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelatedTaskDto;
import ru.progwards.tasktracker.controller.dto.RelationTypeDto;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskDtoConverter implements Converter<RelatedTask, RelatedTaskDto> {

    @Autowired
    private Converter<RelationType, RelationTypeDto> relationTypeDtoConverter;

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
                    relationTypeDtoConverter.toModel(dto.getRelationType()),
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
                    relationTypeDtoConverter.toDto(model.getRelationType()),
                    model.getCurrentTaskId(),
                    model.getAttachedTaskId()
            );
    }
}
