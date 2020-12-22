package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.model.TaskPriority;

import java.util.Collection;

/**
 * Репозиторий TaskPriority
 * @author Pavel Khovaylo
 */
@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
public class TaskPriorityRepository implements Repository<Long, TaskPriority> {

//    @Autowired
//    private TaskTypeRepository<TaskPriority, Long> repository;

    /**
     * @return
     */
    @Override
    public Collection<TaskPriority> get() {
//        return TaskPriorityJsonHandler.getMap().values().stream().collect(Collectors.toUnmodifiableList());
        return null;
    }

    /**
     * @param id идентификатор
     * @return
     */
    @Override
    public TaskPriority get(Long id) {
//        return id == null ? null : TaskPriorityJsonHandler.getMap().get(id);
        return null;
    }

    /**
     * @param entity новая сущность
     */
    @Override
    public void create(TaskPriority entity) {
//        if (entity != null) {
//            if (entity.getId() == null)
//                entity.setId(new Random().nextLong());
//            TaskPriority newEntity = TaskPriorityJsonHandler.getMap().put(entity.getId(), entity);
//            if (newEntity == null)
//                TaskPriorityJsonHandler.write();
//        }
    }

    /**
     * @param entity измененная сущность
     */
    @Override
    public void update(TaskPriority entity) {
        if (entity != null) {
            delete(entity.getId());
            create(entity);
        }
    }

    /**
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
//        if (id != null) {
//            TaskPriority entity = TaskPriorityJsonHandler.getMap().remove(id);
//            if (entity != null)
//                TaskPriorityJsonHandler.write();
//        }
    }
}