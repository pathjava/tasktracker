package service.facade;

import service.vo.TaskModel;
import util.types.Priority;
import service.vo.UserModel;
import util.types.Status;

public interface CreateService<M> {
    void create(M model);
}
