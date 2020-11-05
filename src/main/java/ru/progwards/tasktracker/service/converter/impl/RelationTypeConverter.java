package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelationType;

/**
 * конвертер valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@Component
public class RelationTypeConverter implements Converter<RelationTypeEntity, RelationType> {

    /**
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public RelationType toVo(RelationTypeEntity entity) {
        if (entity == null)
            return null;
        else
            return new RelationType(
                    entity.getId(),
                    entity.getName(),
                    toVo(entity.getCounterRelation())
            );
    }

    /**
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public RelationTypeEntity toEntity(RelationType valueObject) {
        if (valueObject == null)
            return null;
        else
            return new RelationTypeEntity(
                    checkIdNotNull(valueObject),
                    valueObject.getName(),
                    toEntity(valueObject.getCounterRelation())
            );
    }

    private Long checkIdNotNull(RelationType valueObject) {
        return valueObject.getId() == null ? null : valueObject.getId();
    }
}
