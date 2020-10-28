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

    @Override
    public void refresh(Task model) {
        taskRepository.update(converterTask.toEntity(model));
    }
}
