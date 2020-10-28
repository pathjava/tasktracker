package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.TaskEntityJsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskEntity;

import java.util.Collection;

@Component
public class TaskEntityByCodeRepository implements Repository<String, TaskEntity> {

    private JsonHandler<Long, TaskEntity> jsonHandler;

    @Autowired
    public void setJsonHandler(TaskEntityJsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    @Override
    public Collection<TaskEntity> get() {
        return null;
    }

    /**
     * @param code код задачи, генерируемый на основе префикса проекта и идентификатора
     * @return возвращает сущность из БД
     */
    @Override
    public TaskEntity get(String code) {
        return jsonHandler.getMap().values().stream()
                .filter(entity -> entity.getCode().equals(code))
                .findFirst().orElse(null);
    }

    @Override
    public void create(TaskEntity elem) {
    }

    @Override
    public void update(TaskEntity elem) {
    }

    @Override
    public void delete(String id) {
    }
}
