package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.WorkLog;

/**
 * конвертеры valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@Component
public class WorkLogConverter implements Converter<WorkLogEntity, WorkLog> {
    /**
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public WorkLog toVo(WorkLogEntity entity) {
        if (entity == null)
            return null;
        else
            return new WorkLog(
                    entity.getId(),
                    entity.getTaskId(),
                    entity.getSpent(),
                    entity.getWorker(),
                    entity.getWhen(),
                    entity.getDescription(),
                    entity.getEstimateChange(),
                    entity.getEstimateValue()
            );
    }

    /**
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public WorkLogEntity toEntity(WorkLog valueObject) {
        if (valueObject == null)
            return null;
        else
            return new WorkLogEntity(
                    valueObject.getId(),
                    valueObject.getTaskId(),
                    valueObject.getSpent(),
                    valueObject.getWorker(),
                    valueObject.getWhen(),
                    valueObject.getDescription(),
                    valueObject.getEstimateChange(),
                    valueObject.getEstimateValue()
            );
    }
}
