package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskTypeEntity;
import ru.progwards.tasktracker.repository.entity.WorkFlowEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.TaskType;
import ru.progwards.tasktracker.service.vo.WorkFlow;

/**
 * Конвертеры valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskTypeConverter implements Converter<TaskTypeEntity, TaskType> {

    @Autowired
    private Repository<Long, TaskTypeEntity> taskTypeEntityRepository;
    @Autowired
    private Repository<Long, WorkFlowEntity> workFlowRepository;
    @Autowired
    private Converter<WorkFlowEntity, WorkFlow> workFlowConverter;

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
        else {
            WorkFlow workFlow = workFlowConverter.toVo(workFlowRepository.get(entity.getWorkFlow_id()));
            return new TaskType(
                    entity.getId(),
                    workFlow,
                    entity.getName()
            );
        }
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
        else {
            TaskTypeEntity taskTypeEntity = taskTypeEntityRepository.get(valueObject.getId());
            return new TaskTypeEntity(
                    valueObject.getId(),
                    taskTypeEntity.getProject_id(),
                    valueObject.getWorkFlow().getId(),
                    valueObject.getName()
            );
        }
    }
}
