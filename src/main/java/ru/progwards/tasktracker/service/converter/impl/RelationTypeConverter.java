package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelationType;

@Component
public class RelationTypeConverter implements Converter<RelationTypeEntity, RelationType> {

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

    @Override
    public RelationTypeEntity toEntity(RelationType relationType) {
        if (relationType == null)
            return null;
        else
            return new RelationTypeEntity(
                    relationType.getId(),
                    relationType.getName(),
                    toEntity(relationType.getCounterRelation())
            );
    }
}
