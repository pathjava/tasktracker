package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByProjectId;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskTypeEntity;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Методы работы сущности с базой данных
 *
 * @author Oleg Kiselev
 */
@org.springframework.stereotype.Repository
@Deprecated
public class TaskTypeEntityRepository implements Repository<Long, TaskTypeEntity>,
        RepositoryByProjectId<Long, TaskTypeEntity> {

    @Autowired
    private JsonHandler<Long, TaskTypeEntity> jsonHandler;

    /**
     * Метод получения всех типов задач
     *
     * @return коллекция всех TaskTypeEntity
     */
    @Override
    public Collection<TaskTypeEntity> get() {
        return jsonHandler.getMap().values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Метод получения типа задачи по идентификатору
     *
     * @param id идентификатор типа задачи
     * @return возвращает полученный тип задачи
     */
    @Override
    public TaskTypeEntity get(Long id) {
        return jsonHandler.getMap().get(id);
    }

    /**
     * Метод создания типа задачи
     *
     * @param entity новая сущность
     */
    @Override
    public void create(TaskTypeEntity entity) {
        TaskTypeEntity type = jsonHandler.getMap().put(entity.getId(), entity);
        if (type == null)
            jsonHandler.write();
    }

    /**
     * Метод обновления типа задачи
     *
     * @param entity измененная сущность
     */
    @Override
    public void update(TaskTypeEntity entity) {
        jsonHandler.getMap().remove(entity.getId());
        create(entity);
    }

    /**
     * Метод удаления типа задачи по идентификатору
     *
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
        TaskTypeEntity entity = get(id);
        if (entity != null){
            jsonHandler.getMap().remove(entity.getId());
            jsonHandler.write();
        }
    }

    /**
     * Метод получения коллекции типов задач по идентификатору проекта
     *
     * @param projectId идентификатор проекта
     * @return коллекция типов задач проекта
     */
    @Override
    public Collection<TaskTypeEntity> getByProjectId(Long projectId) {
        return jsonHandler.getMap().values().stream()
                .filter(entity -> entity.getProject().getId().equals(projectId))
                .collect(Collectors.toList());
    }
}
