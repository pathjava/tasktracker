package repository.dao;


import repository.entity.Task;
import service.vo.TaskModel;

import java.util.Collection;

public class TaskRepository implements Repository<Task, TaskModel>{

    @Override
    public Collection<TaskModel> get() {
        return null;
    }

    @Override
    public TaskModel get(Task id) {
        return null;
    }

    @Override
    public TaskModel save(Task id) {
        return null;
    }
}

