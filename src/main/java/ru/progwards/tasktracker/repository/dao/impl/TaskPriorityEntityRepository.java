package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.TaskPriorityEntityJsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskPriorityEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TaskPriorityEntityRepository implements Repository<Long, TaskPriorityEntity> {

    private final TaskPriorityEntityJsonHandler jsonHandlerTaskPriorityEntity;

    public TaskPriorityEntityRepository(TaskPriorityEntityJsonHandler jsonHandlerTaskPriorityEntity) {
        this.jsonHandlerTaskPriorityEntity = jsonHandlerTaskPriorityEntity;
    }

    @Override
    public Collection<TaskPriorityEntity> get() {
        return jsonHandlerTaskPriorityEntity.getMap().values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public TaskPriorityEntity get(Long id) {
        return id == null ? null : jsonHandlerTaskPriorityEntity.getMap().get(id);
    }

    @Override
    public void create(TaskPriorityEntity entity) {
        if (entity != null) {
            TaskPriorityEntity newEntity = jsonHandlerTaskPriorityEntity.getMap().put(entity.getId(), entity);
            if (newEntity == null)
                jsonHandlerTaskPriorityEntity.write();
        }
    }

    @Override
    public void update(TaskPriorityEntity entity) {
        if (entity != null) {
            delete(entity.getId());
            create(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            TaskPriorityEntity entity = jsonHandlerTaskPriorityEntity.getMap().remove(id);
            if (entity != null)
                jsonHandlerTaskPriorityEntity.write();
        }
    }
}