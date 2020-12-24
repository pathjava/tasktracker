package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.RelationTypeEntity;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Методы для работы с БД для сущности типа отношения связанных задач
 *
 * @author Oleg Kiselev
 */
@org.springframework.stereotype.Repository
@Deprecated
public class RelationTypeEntityRepository implements Repository<Long, RelationTypeEntity> {

    @Autowired
    private JsonHandler<Long, RelationTypeEntity> jsonHandler;

    /**
     * Метод получения коллекции сущностей типов отношений связанных задач
     *
     * @return коллекция сущностей
     */
    @Override
    public Collection<RelationTypeEntity> get() {
        return jsonHandler.getMap().values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Метод получения сущности типа отношения связанных задач
     *
     * @param id идентификатор сущности, которую необходимо получить
     * @return полученная из БД сущность
     */
    @Override
    public RelationTypeEntity get(Long id) {
        return jsonHandler.getMap().get(id);
    }

    /**
     * Метод создания сущности типа отношения связанных задач
     *
     * @param entity новая сущность
     */
    @Override
    public void create(RelationTypeEntity entity) {
        RelationTypeEntity typeEntity = jsonHandler.getMap().put(entity.getId(), entity);
        if (typeEntity == null)
            jsonHandler.write();
    }

    /**
     * Метод изменения сущности типа отношения связанных задач
     *
     * @param entity измененная сущность
     */
    @Override
    public void update(RelationTypeEntity entity) {
        jsonHandler.getMap().remove(entity.getId());
        create(entity);
    }

    /**
     * Метод удаления сущности типа отношения связанных задач
     *
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
        RelationTypeEntity entity = get(id);
        if (entity != null) {
            jsonHandler.getMap().remove(entity.getId());
            jsonHandler.write();
        }
    }
}
