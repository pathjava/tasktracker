package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.TaskRepository;
import ru.progwards.tasktracker.service.converter.impl.ConverterTask;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TaskGetListService implements GetListService<Task> {

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
    public Collection<Task> getList() {
        return taskRepository.get().stream()
                .map(entity -> converterTask.toVo(entity))
                .collect(Collectors.toList());
    }
}
