package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

/**
 * Конвертеры valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskConverter implements Converter<RelatedTaskEntity, RelatedTask> {

    @Autowired
    private Converter<RelationTypeEntity, RelationType> converter;

    /**
     * Метод конвертирует сущность Entity в бизнес объект
     *
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public RelatedTask toVo(RelatedTaskEntity entity) {
        if (entity == null)
            return null;
        else
            return new RelatedTask(
                    entity.getId(),
                    converter.toVo(entity.getRelationTypeEntity()),
                    entity.getCurrentTaskId(),
                    entity.getAttachedTaskId()
            );
    }

    /**
     * Метод конвертирует бизнес объект в сущность Entity
     *
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public RelatedTaskEntity toEntity(RelatedTask valueObject) {
        if (valueObject == null)
            return null;
        else
            return new RelatedTaskEntity(
                    valueObject.getId(),
                    converter.toEntity(valueObject.getRelationType()),
                    valueObject.getCurrentTaskId(),
                    valueObject.getAttachedTaskId()
            );
    }
}
