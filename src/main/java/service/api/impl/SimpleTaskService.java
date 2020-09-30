package service.api.impl;

import repository.dao.TaskRepository;
import repository.entity.Task;
import service.api.TaskService;
import service.converter.Converter;
import service.vo.Priority;
import service.vo.TaskModel;
import service.vo.UserModel;

public class SimpleTaskService implements TaskService {
    private TaskRepository repo;
    private Converter<Task, TaskModel> converter;

    public void createTask(TaskModel model) {
        repo.save(converter.convertFrom(model));
    }

    public void setUser(UserModel model, TaskModel model2) {
        //
    }

    public void setPriority(Priority priority, TaskModel model) {

    }
}
