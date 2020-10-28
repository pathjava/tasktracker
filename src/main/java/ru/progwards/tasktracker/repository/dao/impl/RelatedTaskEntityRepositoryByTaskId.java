package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.RelatedTaskEntityJsonHandler;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class RelatedTaskEntityRepositoryByTaskId implements RepositoryByTaskId<Long, RelatedTaskEntity> {

    private JsonHandler<Long, RelatedTaskEntity> jsonHandler;

    @Autowired
    public void setJsonHandler(RelatedTaskEntityJsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    @Override
    public Collection<RelatedTaskEntity> getByTaskId(Long taskId) {
        if (taskId == null)
            return null;

        Collection<RelatedTaskEntity> collection = jsonHandler.getMap().values().stream()
                .filter(value -> value.getTaskId().equals(taskId))
                .collect(Collectors.toList());

        return collection.size() == 0 ? null : collection;
    }
}
