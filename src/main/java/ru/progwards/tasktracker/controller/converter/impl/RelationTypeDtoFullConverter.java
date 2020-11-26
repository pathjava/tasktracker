package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelationTypeDtoFull;
import ru.progwards.tasktracker.service.vo.RelationType;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class RelationTypeDtoFullConverter implements Converter<RelationType, RelationTypeDtoFull> {

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public RelationType toModel(RelationTypeDtoFull dto) {
        if (dto == null)
            return null;
        else
            return new RelationType(
                    dto.getId(),
                    dto.getName(),
                    toModel(dto.getCounterRelation())
            );
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public RelationTypeDtoFull toDto(RelationType model) {
        if (model == null)
            return null;
        else
            return new RelationTypeDtoFull(
                    model.getId(),
                    model.getName(),
                    toDto(model.getCounterRelation())
            );
    }
}
