package service.api;

import service.vo.TaskModel;
import util.types.TaskStatus;

public interface ChangeTaskService {
    void changeStatus(TaskStatus status, TaskModel model);
}
