package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.TaskRepository;
import ru.progwards.tasktracker.service.converter.impl.ConverterTask;
import ru.progwards.tasktracker.service.facade.RefreshService;
import ru.progwards.tasktracker.service.vo.Task;

@Service
public class TaskRefreshService implements RefreshService<Task> {

    private TaskRepository taskRepository;
    private ConverterTask converterTask;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setConverterTask(ConverterTask converterTask) {
        this.converterTask = converterTask;
    }

    @Override
    public void refresh(Task model) {
        taskRepository.update(converterTask.toEntity(model));
    }
}
