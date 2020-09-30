package service.api;

import service.vo.TaskModel;
import util.types.Status;

public interface ChangeTaskService {
    void changeStatus(Status status, TaskModel model);
}
