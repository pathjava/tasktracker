package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.converter.impl.TaskConverter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * создание задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskCreateService implements CreateService<Task> {

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
     * метод конвертирует и создает задачу
     *
     * @param model value object
     */
    @Override
    public void create(Task model) {
        taskRepository.create(converterTask.toEntity(model));
    }
}
