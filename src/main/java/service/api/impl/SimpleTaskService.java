package service.api.impl;

import repository.dao.impl.TaskRepository;
import repository.entity.Task;
import service.api.ChangeTaskService;
import service.converter.Converter;
import util.types.Priority;
import service.vo.TaskModel;
import service.vo.UserModel;

public abstract class SimpleTaskService implements ChangeTaskService {
    private TaskRepository repo;
    private Converter<Task, TaskModel> converter;

    public void createTask(TaskModel model) {
        repo.create(converter.convertFrom(model));
    }

    public void setUser(UserModel model, TaskModel model2) {
        //
    }

    public void setPriority(Priority priority, TaskModel model) {

    }
}
