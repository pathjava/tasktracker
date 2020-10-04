package ru.progwards.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.repository.dao.impl.TaskRepository;
import ru.progwards.service.converter.impl.ConverterTask;
import ru.progwards.service.facade.GetService;
import ru.progwards.service.vo.Task;

@Service
public class TaskGetService implements GetService<Task> {

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
    public Task get(Task model) {
        return converterTask.convertTo(taskRepository.get(model.getId()));
    }
}
