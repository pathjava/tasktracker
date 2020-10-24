package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.TaskConverter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.Task;

@Service
public class TaskCreateService implements CreateService<Task> {

    private TaskEntityRepository taskRepository;
    private TaskConverter converterTask;

    @Autowired
    public void setTaskRepository(TaskEntityRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setConverterTask(TaskConverter converterTask) {
        this.converterTask = converterTask;
    }

    @Override
    public void create(Task model) {
        taskRepository.create(converterTask.toEntity(model));
    }
}
