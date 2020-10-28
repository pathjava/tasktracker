package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskCode;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.TaskEntityJsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskEntity;

@Component
public class TaskRepositoryByTaskCode implements RepositoryByTaskCode<String, TaskEntity> {

    private JsonHandler<Long, TaskEntity> jsonHandler;

    @Autowired
    public void setJsonHandler(TaskEntityJsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    /**
     * @param code код задачи, генерируемый на основе префикса проекта и идентификатора
     * @return возвращает сущность из БД
     */
    @Override
    public TaskEntity getByCode(String code) {
        return jsonHandler.getMap().values().stream()
                .filter(entity -> entity.getCode().equals(code))
                .findFirst().orElse(null);
    }
}
