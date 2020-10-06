package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.TaskRepository;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.Task;

@Service
public class TaskRemoveService implements RemoveService<Task> {

    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void remove(Task model) {
        taskRepository.delete(model.getId());
    }
}
