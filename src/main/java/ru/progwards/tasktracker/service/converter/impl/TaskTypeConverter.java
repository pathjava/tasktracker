package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.TaskTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.TaskType;

/**
 * Конвертеры valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskTypeConverter implements Converter<TaskTypeEntity, TaskType> {

    /**
     * Метод конвертирует сущность Entity в бизнес объект
     *
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public TaskType toVo(TaskTypeEntity entity) {
        if (entity == null)
            return null;
        else
            return new TaskType(
                    entity.getId(),
                    entity.getProject_id(),
                    entity.getWorkFlow_id(),
                    entity.getName()
            );
    }

    /**
     * Метод конвертирует бизнес объект в сущность Entity
     *
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public TaskTypeEntity toEntity(TaskType valueObject) {
        if (valueObject == null)
            return null;
        else
            return new TaskTypeEntity(
                    valueObject.getId(),
                    valueObject.getProject_id(),
                    valueObject.getWorkFlow_id(),
                    valueObject.getName()
            );
    }
}
