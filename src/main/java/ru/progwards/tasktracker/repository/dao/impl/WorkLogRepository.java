package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;

import java.util.Collection;

/**
 * Методы работы сущности с базой данных
 *
 * @author Oleg Kiselev
 */
@Component
public class WorkLogRepository implements Repository<Long, WorkLogEntity> {

    private JsonHandler<Long, WorkLogEntity> jsonHandler;

    @Autowired
    public void setJsonHandler(JsonHandler<Long, WorkLogEntity> jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    @Override
    public Collection<WorkLogEntity> get() {
        return null;
    }

    /**
     * @param id идентификатор по которому надо получить лог
     * @return найденный лог
     */
    @Override
    public WorkLogEntity get(Long id) {
        return jsonHandler.getMap().get(id);
    }

    /**
     * @param entity новая сущность
     */
    @Override
    public void create(WorkLogEntity entity) {
        WorkLogEntity logEntity = jsonHandler.getMap().put(entity.getId(), entity);
        if (logEntity == null)
            jsonHandler.write();
    }

    @Override
    public void update(WorkLogEntity entity) {
    }

    /**
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
        WorkLogEntity logEntity = get(id);
        if (logEntity != null)
            jsonHandler.getMap().remove(id);
    }
}
