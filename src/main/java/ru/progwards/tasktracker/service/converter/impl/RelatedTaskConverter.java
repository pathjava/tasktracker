package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

@Component
public class RelatedTaskConverter implements Converter<RelatedTaskEntity, RelatedTask> {

    private Converter<RelationTypeEntity, RelationType> relationTypeConverter;

    @Autowired
    public void setRelationTypeConverter(Converter<RelationTypeEntity, RelationType> relationTypeConverter) {
        this.relationTypeConverter = relationTypeConverter;
    }

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

    @Override
    public RelatedTaskEntity toEntity(RelatedTask relatedTask) {
        if (relatedTask == null)
            return null;
        else
            return new RelatedTaskEntity(
                    relatedTask.getId(),
                    relationTypeConverter.toEntity(relatedTask.getRelationType()),
                    relatedTask.getParentTaskId(),
                    relatedTask.getTaskId()
            );
    }
}
