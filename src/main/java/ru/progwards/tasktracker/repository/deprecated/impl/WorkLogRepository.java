package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.model.WorkLog;

import java.util.Collection;

/**
 * Методы работы сущности с базой данных
 *
 * @author Oleg Kiselev
 */
@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
public class WorkLogRepository implements Repository<Long, WorkLog>,
        RepositoryByTaskId<Long, WorkLog> {

//    @Autowired
//    private TaskTypeRepository<WorkLog, Long> repository;

    @Override
    public Collection<WorkLog> get() {
        return null;
    }

    /**
     * Метод получения лога по идентификатору
     *
     * @param id идентификатор по которому надо получить лог
     * @return найденный лог
     */
    @Override
    public WorkLog get(Long id) {
//        return jsonHandler.getMap().get(id);
        return null;
    }

    /**
     * Метод создания сущности лога
     *
     * @param entity новая сущность
     */
    @Override
    public void create(WorkLog entity) {
//        WorkLog logEntity = jsonHandler.getMap().put(entity.getId(), entity);
//        if (logEntity == null)
//            jsonHandler.write();
    }

    /**
     * Метод обновления сущности лога
     *
     * @param entity обновленная сущность
     */
    @Override
    public void update(WorkLog entity) {
//        jsonHandler.getMap().remove(entity.getId());
//        create(entity);
    }

    /**
     * Метод удаления сущности лога по идентификатору
     *
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
//        WorkLog entity = get(id);
//        if (entity != null) {
//            jsonHandler.getMap().remove(id);
//            jsonHandler.write();
//        }
    }

    /**
     * Метод получения коллекции логов по идентификатору задачи
     *
     * @param taskId идентификатор задачи для которой ищем логи
     * @return коллекция логов
     */
    @Override
    public Collection<WorkLog> getByTaskId(Long taskId) {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> entity.getTask().getId().equals(taskId))
//                .collect(Collectors.toList());
        return null;
    }
}
