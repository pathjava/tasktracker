package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Методы работы сущности с базой данных
 *
 * @author Oleg Kiselev
 */
@org.springframework.stereotype.Repository
public class WorkLogEntityRepository implements Repository<Long, WorkLogEntity>,
        RepositoryByTaskId<Long, WorkLogEntity> {

    @Autowired
    private JsonHandler<Long, WorkLogEntity> jsonHandler;

    @Override
    public Collection<WorkLogEntity> get() {
        return null;
    }

    /**
     * Метод получения лога по идентификатору
     *
     * @param id идентификатор по которому надо получить лог
     * @return найденный лог
     */
    @Override
    public WorkLogEntity get(Long id) {
        return jsonHandler.getMap().get(id);
    }

    /**
     * Метод создания сущности лога
     *
     * @param entity новая сущность
     */
    @Override
    public void create(WorkLogEntity entity) {
        WorkLogEntity logEntity = jsonHandler.getMap().put(entity.getId(), entity);
        if (logEntity == null)
            jsonHandler.write();
    }

    /**
     * Метод обновления сущности лога
     *
     * @param entity обновленная сущность
     */
    @Override
    public void update(WorkLogEntity entity) {
        jsonHandler.getMap().remove(entity.getId());
        create(entity);
    }

    /**
     * Метод удаления сущности лога по идентификатору
     *
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
        WorkLogEntity entity = get(id);
        if (entity != null) {
            jsonHandler.getMap().remove(id);
            jsonHandler.write();
        }
    }

    /**
     * Метод получения коллекции логов по идентификатору задачи
     *
     * @param taskId идентификатор задачи для которой ищем логи
     * @return коллекция логов
     */
    @Override
    public Collection<WorkLogEntity> getByTaskId(Long taskId) {
        return jsonHandler.getMap().values().stream()
                .filter(entity -> entity.getTask().equals(taskId))
                .collect(Collectors.toList());
    }
}
