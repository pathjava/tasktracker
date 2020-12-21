package ru.progwards.tasktracker.repository.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelationType;

/**
 * Конвертеры valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@Component
public class RelationTypeConverter implements Converter<RelationTypeEntity, RelationType> {

    /**
     * Метод конвертирует сущность Entity в бизнес объект
     *
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public RelationType toVo(RelationTypeEntity entity) {
//        if (entity == null)
//            return null;
//        else
//            return new RelationType(
//                    entity.getId(),
//                    entity.getName(),
//                    toVo(entity.getCounterRelation()),
//                    null //TODO - check
//            );
        return null;
    }

    /**
     * Метод конвертирует бизнес объект в сущность Entity
     *
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public RelationTypeEntity toEntity(RelationType valueObject) {
//        if (valueObject == null)
//            return null;
//        else
//            return new RelationTypeEntity(
//                    valueObject.getId(),
//                    valueObject.getName(),
//                    toEntity(valueObject.getCounterRelation())
//            );
        return null;
    }
}
