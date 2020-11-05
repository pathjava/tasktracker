package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.RelatedTaskEntityJsonHandler;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * получение списка связанных задач для определенной задачи
 *
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskEntityRepositoryByTaskId implements RepositoryByTaskId<Long, RelatedTaskEntity> {

    private JsonHandler<Long, RelatedTaskEntity> jsonHandler;

    @Autowired
    public void setJsonHandler(RelatedTaskEntityJsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    /**
     * @param taskId идентификатор задачи для которой необходимо получить связанные задачи
     * @return возвращается коллекция (может быть пустой) связанных задач
     */
    @Override
    public Collection<RelatedTaskEntity> getByTaskId(Long taskId) {
        //TODO - проверить возможность попадания в связанные задачи задач, помеченных как удаленные
        return jsonHandler.getMap().values().stream()
                .filter(value -> value.getTaskId().equals(taskId))
                .collect(Collectors.toList());
    }
}
