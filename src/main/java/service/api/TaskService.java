package service.api;

import service.vo.TaskModel;

public interface TaskService {
    void createTask(TaskModel model);
    void setUser(UserModel model, TaskModel model);
    void setPriority(Priority priority, TaskModel model);
}
