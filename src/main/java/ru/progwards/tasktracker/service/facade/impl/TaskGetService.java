package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.TaskRepository;
import ru.progwards.tasktracker.service.converter.impl.ConverterTask;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Task;

@Service
public class TaskGetService implements GetService<Long, Task> {

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
    public Task get(Long id) {
        return converterTask.toVo(taskRepository.get(id));
    }
}
