package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByAttachedTaskId;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Методы для работы с БД для сущности связанной задачи RelatedTaskEntity
 *
 * @author Oleg Kiselev
 */
@org.springframework.stereotype.Repository
public class RelatedTaskEntityRepository implements Repository<Long, RelatedTaskEntity>,
        RepositoryByTaskId<Long, RelatedTaskEntity>, RepositoryByAttachedTaskId<Long, RelatedTaskEntity> {

    @Autowired
    private JsonHandler<Long, RelatedTaskEntity> jsonHandler;

    /**
     * Метод получения всех связанных задач из базы данных без привязки к какой-либо задаче
     *
     * @return коллекция сущностей
     */
    @Override
    public Collection<RelatedTaskEntity> get() {
        return jsonHandler.getMap().values();
    }

    /**
     * Метод получения связанной задачи по её идентификатору
     *
     * @param id идентификатор связанной задачи
     * @return связанную задачу
     */
    @Override
    public RelatedTaskEntity get(Long id) {
        return jsonHandler.getMap().get(id);
    }

    /**
     * Метод создания и записи в БД сущности RelatedTaskEntity
     *
     * @param entity сущность с данными связанной задачи
     */
    @Override
    public void create(RelatedTaskEntity entity) {
        RelatedTaskEntity relatedTask = jsonHandler.getMap().put(entity.getId(), entity);
        if (relatedTask == null)
            jsonHandler.write();
    }

    @Override
    public void update(RelatedTaskEntity entity) {
    }

    /**
     * Метод удаления связанных задач по идентификатору из параметра метода
     *
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
        RelatedTaskEntity entity = jsonHandler.getMap().get(id);
        if (entity != null) {
            jsonHandler.getMap().remove(id);
            jsonHandler.write();
        }
    }

    /**
     * Метод получения коллекции связанных задач по идентификатору задачи
     *
     * @param taskId идентификатор задачи для которой необходимо получить связанные задачи
     * @return возвращается коллекция (может быть пустой) связанных задач
     */
    @Override
    public Collection<RelatedTaskEntity> getByTaskId(Long taskId) {
        return jsonHandler.getMap().values().stream()
                .filter(entity -> entity.getCurrentTaskId().equals(taskId))
                .collect(Collectors.toList());
    }

    /**
     * Метод получения всех входящих RelatedTaskEntity для определенной задачи
     *
     * @param taskId идентификатор задачи
     * @return коллекция entity
     */
    @Override
    public Collection<RelatedTaskEntity> getByAttachedTaskId(Long taskId) {
        return jsonHandler.getMap().values().stream()
                .filter(entity -> entity.getAttachedTaskId().equals(taskId))
                .collect(Collectors.toList());
    }
}
