package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

/**
 * конвертер valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskConverter implements Converter<RelatedTaskEntity, RelatedTask> {

    private Converter<RelationTypeEntity, RelationType> relationTypeConverter;

    @Autowired
    public void setRelationTypeConverter(Converter<RelationTypeEntity, RelationType> relationTypeConverter) {
        this.relationTypeConverter = relationTypeConverter;
    }

    /**
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
                    relationTypeConverter.toVo(entity.getRelationTypeEntity()),
                    entity.getParentTaskId(),
                    entity.getTaskId()
            );
    }

    /**
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public RelatedTaskEntity toEntity(RelatedTask valueObject) {
        if (valueObject == null)
            return null;
        else
            return new RelatedTaskEntity(
                    checkIdNotNull(valueObject),
                    relationTypeConverter.toEntity(valueObject.getRelationType()),
                    valueObject.getParentTaskId(),
                    valueObject.getTaskId()
            );
    }

    private Long checkIdNotNull(RelatedTask valueObject) {
        return valueObject.getId() == null ? null : valueObject.getId();
    }
}
