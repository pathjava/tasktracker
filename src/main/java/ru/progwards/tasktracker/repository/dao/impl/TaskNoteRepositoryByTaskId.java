package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.TaskNoteEntity;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * получение списка комментариев для определенной задачи
 *
 * @author Konstantin Kishkin
 */
@Component
public class TaskNoteRepositoryByTaskId implements RepositoryByTaskId<Long, TaskNoteEntity> {

    private JsonHandler<Long, TaskNoteEntity> jsonHandler;

    @Autowired
    public void setJsonHandler(JsonHandler<Long, TaskNoteEntity> jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    /**
     * @param taskId идентификатор задачи для которой ищем комментарии
     * @return коллекция комментариев
     */
    @Override
    public Collection<TaskNoteEntity> getByTaskId(Long taskId) {
        return jsonHandler.getMap().values().stream()
                .filter(value -> value.getTask_id().equals(taskId))
                .collect(Collectors.toList());
    }
}

