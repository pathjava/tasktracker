package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskNoteEntity;
import ru.progwards.tasktracker.repository.deprecated.jsonhandler.TaskNotesEntityJsonHandler;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Методы работы сущности с базой данных
 *
 * @author Konstantin Kishkin
 */
public class TaskNoteRepository implements Repository<Long, TaskNoteEntity> {

    private JsonHandler<Long, TaskNoteEntity> jsonHandler;

    @Autowired
    public void setJsonHandler(TaskNotesEntityJsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    /**
     * @return возвращаем коллекцию всех комментариев
     */
    @Override
    public Collection<TaskNoteEntity> get() {
        return jsonHandler.getMap().values().stream()
                .collect(Collectors.toList());
    }

    /**
     * @param id идентификатор комментария, который необходимо получить
     * @return возвращаем комментарий, полученный по идентификатору
     */
    @Override
    public TaskNoteEntity get(Long id) {
        TaskNoteEntity task = jsonHandler.getMap().get(id);
        return task == null ? null : task;
    }

    /**
     * @param entity создаем/записываем в репозиторий новый комментарий
     */
    @Override
    public void create(TaskNoteEntity entity) {
        TaskNoteEntity task = jsonHandler.getMap().put(entity.getId(), entity);
        if (task == null)
            jsonHandler.write();
    }

    /**
     * @param entity обновляем полученный комментарий в репозитории
     */
    @Override
    public void update(TaskNoteEntity entity) {
        jsonHandler.getMap().remove(entity.getId());
        entity.setUpdated(ZonedDateTime.now().toEpochSecond());
        create(entity);
    }

    /**
     * @param id идентификатор по которому удаляем комменртарий
     */
    @Override
    public void delete(Long id) {
        TaskNoteEntity task = get(id);
        if (task != null) {
            jsonHandler.getMap().remove(id);
            create(task);
        }
    }
}

