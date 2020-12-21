package ru.progwards.tasktracker.repository.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * Конвертеры valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskConverter implements Converter<RelatedTaskEntity, RelatedTask> {

    @Autowired
    private Converter<RelationTypeEntity, RelationType> typeConverter;
    @Autowired
    private Converter<TaskEntity, Task> taskConverter;

    /**
     * Метод конвертирует сущность Entity в бизнес объект
     *
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public RelatedTask toVo(RelatedTaskEntity entity) {
//        if (entity == null)
//            return null;
//        else {
//            return new RelatedTask(
//                    entity.getId(),
//                    typeConverter.toVo(entity.getRelationTypeEntity()),
//                    taskConverter.toVo(entity.getCurrentTask()),
//                    taskConverter.toVo(entity.getAttachedTask())
//            );
//        }
        return null;
    }

    /**
     * Метод конвертирует бизнес объект в сущность Entity
     *
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public RelatedTaskEntity toEntity(RelatedTask valueObject) {
//        if (valueObject == null)
//            return null;
//        else
//            return new RelatedTaskEntity(
//                    valueObject.getId(),
//                    typeConverter.toEntity(valueObject.getRelationType()),
//                    taskConverter.toEntity(valueObject.getCurrentTask()),
//                    taskConverter.toEntity(valueObject.getAttachedTask()),
//                    false
//            );
        return null;
    }
}
