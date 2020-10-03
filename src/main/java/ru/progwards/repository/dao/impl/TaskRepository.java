package ru.progwards.repository.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.repository.dao.Repository;
import ru.progwards.repository.entity.TaskEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TaskRepository implements Repository<Long, TaskEntity> {

    private JsonHandlerImpl jsonHandler;

    @Autowired
    public void setJsonHandler(JsonHandlerImpl jsonHandler) {
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
    public void create(TaskEntity task) {
        TaskEntity temp = jsonHandler.tasks.put(task.getId(), task);
        if (temp == null)
            jsonHandler.write();
    }

    @Override
    public void update(TaskEntity task) {
        create(task);
    }

    @Override
    public void delete(Long id) {
        TaskEntity temp = jsonHandler.tasks.remove(id);
        if (temp != null)
            jsonHandler.write();
    }

}

