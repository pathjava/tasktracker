package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.RelatedTaskEntityJsonHandler;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;

import java.util.Collection;

/**
 * методы для работы с БД для сущности связанной задачи RelatedTaskEntity
 *
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskEntityRepository implements Repository<Long, RelatedTaskEntity> {

    private JsonHandler<Long, RelatedTaskEntity> jsonHandler;

    @Autowired
    public void setJsonHandler(RelatedTaskEntityJsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    @Override
    public Collection<RelatedTaskEntity> get() {
        return jsonHandler.getMap().values();
    }

    /**
     * метод получения связанной задачи по её идентификатору
     *
     * @param id идентификатор связанной задачи
     * @return связанную задачу
     */
    @Override
    public RelatedTaskEntity get(Long id) {
        return jsonHandler.getMap().get(id);
    }

    /**
     * метод создания и записи в БД сущности RelatedTaskEntity
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
     * метод удаления связанных задач по идентификатору из параметра метода
     *
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
        RelatedTaskEntity entity = jsonHandler.getMap().get(id);
        if (entity != null) {
            for (RelatedTaskEntity value : jsonHandler.getMap().values()) {
                if (value.getParentTaskId().equals(entity.getId()) && value.getTaskId().equals(id))
                    jsonHandler.getMap().remove(value.getId());
            }
            jsonHandler.getMap().remove(id);
            jsonHandler.write();
        }
    }
}
