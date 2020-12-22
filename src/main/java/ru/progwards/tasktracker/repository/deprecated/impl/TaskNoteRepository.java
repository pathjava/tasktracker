package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.model.TaskNote;

import java.util.Collection;

/**
 * Методы работы сущности с базой данных
 *
 * @author Konstantin Kishkin
 */
@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
public class TaskNoteRepository implements Repository<Long, TaskNote>, RepositoryByTaskId<Long, TaskNote> {

//    @Autowired
//    private TaskTypeRepository<TaskNote, Long> repository;

    /**
     * @return возвращаем коллекцию всех комментариев
     */
    @Override
    public Collection<TaskNote> get() {
//        return jsonHandler.getMap().values().stream()
//                .collect(Collectors.toList());
        return null;
    }

    /**
     * @param id идентификатор комментария, который необходимо получить
     * @return возвращаем комментарий, полученный по идентификатору
     */
    @Override
    public TaskNote get(Long id) {
//        TaskNote task = jsonHandler.getMap().get(id);
//        return task == null ? null : task;
        return null;
    }

    /**
     * @param entity создаем/записываем в репозиторий новый комментарий
     */
    @Override
    public void create(TaskNote entity) {
//        TaskNote task = jsonHandler.getMap().put(entity.getId(), entity);
//        if (task == null)
//            jsonHandler.write();
    }

    /**
     * @param entity обновляем полученный комментарий в репозитории
     */
    @Override
    public void update(TaskNote entity) {
//        jsonHandler.getMap().remove(entity.getId());
//        entity.setUpdated(ZonedDateTime.now().toEpochSecond());
//        create(entity);
    }

    /**
     * @param id идентификатор по которому удаляем комменртарий
     */
    @Override
    public void delete(Long id) {
//        TaskNote task = get(id);
//        if (task != null) {
//            jsonHandler.getMap().remove(id);
//            create(task);
//        }
    }

    /**
     * @param taskId идентификатор задачи для которой ищем комментарии
     * @return коллекция комментариев
     */
    @Override
    public Collection<TaskNote> getByTaskId(Long taskId) {
//        return jsonHandler.getMap().values().stream()
//                .filter(value -> value.getTask_id().equals(taskId))
//                .collect(Collectors.toList());
        return null;
    }
}

