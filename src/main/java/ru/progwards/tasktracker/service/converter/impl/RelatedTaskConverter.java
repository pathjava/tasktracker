package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelatedTask;

@Component
public class RelatedTaskConverter implements Converter<RelatedTaskEntity, RelatedTask> {

    private RelationTypeConverter relationTypeConverter;
    private TaskConverter taskConverter;

    @Autowired
    public void setRelationTypeConverter(
            RelationTypeConverter relationTypeConverter,
            TaskConverter taskConverter
    ) {
        this.relationTypeConverter = relationTypeConverter;
        this.taskConverter = taskConverter;
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
                    taskConverter.toVo(entity.getTask())
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
                    taskConverter.toEntity(relatedTask.getTask())
            );
    }
}
