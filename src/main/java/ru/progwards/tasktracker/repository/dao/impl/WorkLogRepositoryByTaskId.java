package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * получение списка логов для определенной задачи
 *
 * @author Oleg Kiselev
 */
@Component
public class WorkLogRepositoryByTaskId implements RepositoryByTaskId<Long, WorkLogEntity> {

    private JsonHandler<Long, WorkLogEntity> jsonHandler;

    @Autowired
    public void setJsonHandler(JsonHandler<Long, WorkLogEntity> jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    /**
     * @param taskId идентификатор задачи для которой ищем логи
     * @return коллекция логов
     */
    @Override
    public Collection<WorkLogEntity> getByTaskId(Long taskId) {
        return jsonHandler.getMap().values().stream()
                .filter(value -> value.getTaskId().equals(taskId))
                .collect(Collectors.toList());
    }
}
