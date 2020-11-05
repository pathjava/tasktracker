package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.converter.impl.TaskConverter;
import ru.progwards.tasktracker.service.facade.RefreshService;
import ru.progwards.tasktracker.service.vo.Task;

import java.time.ZonedDateTime;

/**
 * Бизнес-логика обновления задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskRefreshService implements RefreshService<Task> {

    private Repository<Long, TaskEntity> taskRepository;
    private Converter<TaskEntity, Task> converterTask;

    @Autowired
    public void setTaskRepository(TaskEntityRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setConverterTask(TaskConverter converterTask) {
        this.converterTask = converterTask;
    }

    /**
     * @param task  value object - объект бизнес логики (задача), который необходимо обновить
     */
    @Override
    public void refresh(Task task) {
        task.setUpdated(ZonedDateTime.now());
        taskRepository.update(converterTask.toEntity(task));
    }
}
