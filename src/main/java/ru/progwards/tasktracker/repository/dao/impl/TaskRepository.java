package ru.progwards.tasktracker.repository.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TaskRepository implements Repository<Long, TaskEntity> {

    private JsonHandlerTask jsonHandler;

    @Autowired
    public void setJsonHandler(JsonHandlerTask jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    @Override
    public Collection<TaskEntity> get() {
        return jsonHandler.tasks.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public TaskEntity get(Long id) {
        TaskEntity temp = jsonHandler.tasks.get(id);
        if (temp == null)
            throw new IllegalArgumentException(); //TODO определить своё или более подходящее исключение
        return temp;
    }

    @Override
    public void create(TaskEntity taskEntity) {
        TaskEntity temp = jsonHandler.tasks.put(taskEntity.getId(), taskEntity);
        if (temp == null)
            jsonHandler.write();
    }

    @Override
    public void update(TaskEntity taskEntity) {
        delete(taskEntity.getId());
        create(taskEntity);
    }

    @Override
    public void delete(Long id) {
        TaskEntity temp = jsonHandler.tasks.remove(id);
        if (temp != null)
            jsonHandler.write();
    }

}

