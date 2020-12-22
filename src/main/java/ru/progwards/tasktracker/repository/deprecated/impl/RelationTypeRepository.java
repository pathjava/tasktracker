package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.model.RelationType;

import java.util.Collection;

/**
 * Методы для работы с БД для сущности типа отношения связанных задач
 *
 * @author Oleg Kiselev
 */
@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
public class RelationTypeRepository implements Repository<Long, RelationType> {

//    @Autowired
//    private TaskTypeRepository<RelationType, Long> repository;

    /**
     * Метод получения коллекции сущностей типов отношений связанных задач
     *
     * @return коллекция сущностей
     */
    @Override
    public Collection<RelationType> get() {
//        return jsonHandler.getMap().values().stream()
//                .collect(Collectors.toUnmodifiableList());
        return null;
    }

    /**
     * Метод получения сущности типа отношения связанных задач
     *
     * @param id идентификатор сущности, которую необходимо получить
     * @return полученная из БД сущность
     */
    @Override
    public RelationType get(Long id) {
//        return jsonHandler.getMap().get(id);
        return null;
    }

    /**
     * Метод создания сущности типа отношения связанных задач
     *
     * @param entity новая сущность
     */
    @Override
    public void create(RelationType entity) {
//        RelationType typeEntity = jsonHandler.getMap().put(entity.getId(), entity);
//        if (typeEntity == null)
//            jsonHandler.write();
    }

    /**
     * Метод изменения сущности типа отношения связанных задач
     *
     * @param entity измененная сущность
     */
    @Override
    public void update(RelationType entity) {
//        jsonHandler.getMap().remove(entity.getId());
//        create(entity);
    }

    /**
     * Метод удаления сущности типа отношения связанных задач
     *
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
//        RelationType entity = get(id);
//        if (entity != null) {
//            jsonHandler.getMap().remove(entity.getId());
//            jsonHandler.write();
//        }
    }
}
