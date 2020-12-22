package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByAttachedTaskId;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.model.RelatedTask;

import java.util.Collection;

/**
 * Методы для работы с БД для сущности связанной задачи RelatedTask
 *
 * @author Oleg Kiselev
 */
@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
public class RelatedTaskRepository implements Repository<Long, RelatedTask>,
        RepositoryByTaskId<Long, RelatedTask>, RepositoryByAttachedTaskId<Long, RelatedTask> {

//    @Autowired
//    private TaskTypeRepository<RelatedTask, Long> repository;

    /**
     * Метод получения всех связанных задач из базы данных без привязки к какой-либо задаче
     *
     * @return коллекция сущностей
     */
    @Override
    public Collection<RelatedTask> get() {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> !entity.isDeleted())
//                .collect(Collectors.toList());
        return null;
    }

    /**
     * Метод получения связанной задачи по её идентификатору
     *
     * @param id идентификатор связанной задачи
     * @return связанную задачу
     */
    @Override
    public RelatedTask get(Long id) {
//        RelatedTask relatedTask = jsonHandler.getMap().get(id);
//        return relatedTask == null || relatedTask.isDeleted() ? null : relatedTask;
        return null;
    }

    /**
     * Метод создания и записи в БД сущности RelatedTask
     *
     * @param entity сущность с данными связанной задачи
     */
    @Override
    public void create(RelatedTask entity) {
//        RelatedTask relatedTask = jsonHandler.getMap().put(entity.getId(), entity);
//        if (relatedTask == null)
//            jsonHandler.write();
    }

    @Override
    public void update(RelatedTask entity) {
    }

    /**
     * Метод удаления связанных задач по идентификатору из параметра метода
     *
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
//        RelatedTask entity = jsonHandler.getMap().get(id);
//        if (entity != null) {
//            jsonHandler.getMap().remove(id);
//            entity.setDeleted(true);
//            create(entity);
//        }
    }

    /**
     * Метод получения коллекции связанных задач по идентификатору задачи
     *
     * @param taskId идентификатор задачи для которой необходимо получить связанные задачи
     * @return возвращается коллекция (может быть пустой) связанных задач
     */
    @Override
    public Collection<RelatedTask> getByTaskId(Long taskId) {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> entity.getCurrentTask().getId().equals(taskId) && !entity.isDeleted())
//                .collect(Collectors.toList());
        return null;
    }

    /**
     * Метод получения всех входящих RelatedTask для определенной задачи
     *
     * @param taskId идентификатор задачи
     * @return коллекция entity
     */
    @Override
    public Collection<RelatedTask> getByAttachedTaskId(Long taskId) {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> entity.getAttachedTask().getId().equals(taskId) && !entity.isDeleted())
//                .collect(Collectors.toList());
        return null;
    }
}
