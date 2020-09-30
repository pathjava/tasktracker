package service.api;

import service.vo.Priority;
import service.vo.TaskModel;
import service.vo.UserModel;

public interface TaskService {
    void createTask(TaskModel model);
    void setUser(UserModel model, TaskModel model2);
    void setPriority(Priority priority, TaskModel model);
}
