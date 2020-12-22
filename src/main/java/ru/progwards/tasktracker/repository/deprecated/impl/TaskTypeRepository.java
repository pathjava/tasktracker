package ru.progwards.tasktracker.repository.deprecated.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByProjectId;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Методы работы сущности с базой данных
 *
 * @author Oleg Kiselev
 */
@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskTypeRepository implements Repository<Long, TaskType>, RepositoryByProjectId<Long, TaskType> {

    private final @NonNull TaskTypeRepository repository;

    /**
     * Метод получения всех типов задач
     *
     * @return коллекция всех TaskType
     */
    @Override
    public Collection<TaskType> get() {
//        return jsonHandler.getMap().values().stream()
//                .collect(Collectors.toUnmodifiableList()); //JSON
//        return new ArrayList<>(repository.findAll());
        return null;
    }

    /**
     * Метод получения типа задачи по идентификатору
     *
     * @param id идентификатор типа задачи
     * @return возвращает полученный тип задачи
     */
    @Override
    public TaskType get(Long id) {
//        return jsonHandler.getMap().get(id); //JSON
//        return repository.findById(id).orElse(null); //TODO - what orElse return?
//        return repository.findById(id).orElseThrow(() -> new NotFoundException("TaskType.id = " + id + " not found"));
        return null;
    }

    /**
     * Метод создания типа задачи
     *
     * @param entity новая сущность
     */
    @Transactional
    @Override
    public void create(TaskType entity) {
//        TaskType type = jsonHandler.getMap().put(entity.getId(), entity);
//        if (type == null)
//            jsonHandler.write();
//        TaskType taskType = repository.save(entity);
//        if (taskType.getId() != null)
//            entity.setId(taskType.getId()); // TODO - or else throw exception?
    }

    /* @Transactional
    public <S extends T> S save(S entity) {

        if (entityInformation.isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    } */

    /**
     * Метод обновления типа задачи
     *
     * @param entity измененная сущность
     */
    @Transactional
    @Override
    public void update(TaskType entity) {
//        jsonHandler.getMap().remove(entity.getId());
//        create(entity);
//        repository.save(entity); //TODO - check - how?
    }

    /**
     * Метод удаления типа задачи по идентификатору
     *
     * @param id идентификатор удаляемой сущности
     */
    @Transactional
    @Override
    public void delete(Long id) {
//        TaskType entity = get(id);
//        if (entity != null){
//            jsonHandler.getMap().remove(entity.getId());
//            jsonHandler.write();
//        }
//        repository.deleteById(id); //TODO - check - how?
    }

    /**
     * Метод получения коллекции типов задач по идентификатору проекта
     *
     * @param projectId идентификатор проекта
     * @return коллекция типов задач проекта
     */
    @Override
    public Collection<TaskType> getByProjectId(Long projectId) {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> entity.getProject().getId().equals(projectId))
//                .collect(Collectors.toList());
        return null;
    }
}
