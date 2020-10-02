package service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public void setRepo(TaskRepository repo) {
        this.repo = repo;
    }

    @Autowired
    public void setConverter(Converter<Task, TaskModel> converter) {
        this.converter = converter;
    }

    public void createTask(TaskModel taskModel) {
        repo.create(converter.convertFrom(taskModel));
    }

    public void setUser(UserModel userModel, TaskModel taskModel) {

    }

    public void setPriority(Priority priority, TaskModel taskModel) {

    }
}
