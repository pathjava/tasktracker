package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.ConverterTask;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Task;

@Service
public class TaskGetService implements GetService<Long, Task> {

    private TaskEntityRepository taskRepository;
    private ConverterTask converterTask;

    @Autowired
    public void setTaskRepository(TaskEntityRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setConverterTask(ConverterTask converterTask) {
        this.converterTask = converterTask;
    }

    @Override
    public Task get(Long id) {
        return id == null ? null : converterTask.toVo(taskRepository.get(id));
    }
}
