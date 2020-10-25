package ru.progwards.tasktracker.repository.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.TaskEntityJsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskEntity;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TaskEntityRepository implements Repository<Long, TaskEntity> {

    private TaskEntityJsonHandler jsonHandler;

    @Autowired
    public void setJsonHandler(TaskEntityJsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    /**
     * @return возвращаем коллекцию всех задач
     */
    @Override
    public Collection<TaskEntity> get() {
        Collection<TaskEntity> tasks = jsonHandler.tasks.values().stream()
                .filter(value -> !value.getDeleted())
                .collect(Collectors.toList());
        return tasks.size() != 0 ? tasks : null;
    }

    /**
     * @param id идентификатор задачи, которую необходимо получить
     * @return возвращаем задачу, полученную по идентификатору
     */
    @Override
    public TaskEntity get(Long id) {
        TaskEntity task = jsonHandler.tasks.get(id);
        return task == null || task.getDeleted() ? null : task;
    }

    /**
     * @param taskEntity создаем/записываем в репозиторий новую задачу
     */
    @Override
    public void create(TaskEntity taskEntity) {
        TaskEntity task = jsonHandler.tasks.put(taskEntity.getId(), taskEntity);
        if (task == null)
            jsonHandler.write();
    }

    /**
     * @param taskEntity обновляем полученную задачу в репозитории
     */
    @Override
    public void update(TaskEntity taskEntity) {
        jsonHandler.tasks.remove(taskEntity.getId());
        taskEntity.setUpdated(ZonedDateTime.now().toEpochSecond());
        create(taskEntity);
    }

    /**
     * @param id идентификатор по которому удаляем задачу
     */
    @Override
    public void delete(Long id) {
        TaskEntity task = get(id);
        if (task != null) {
            jsonHandler.tasks.remove(id);
            task.setDeleted(true);
            create(task);
        }
    }

    /**
     * @param code код задачи, генерируемый на основе префикса проекта и идентификатора
     * @return возвращает сущность из БД
     */
    public TaskEntity getTaskEntityByCode(String code) {
        return jsonHandler.tasks.values().stream()
                .filter(entity -> entity.getCode().equals(code))
                .findFirst().orElse(null);
    }
}

