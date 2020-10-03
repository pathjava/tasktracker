package ru.progwards.service.api;

import ru.progwards.service.vo.Task;
import ru.progwards.util.types.TaskStatus;

public interface ChangeTaskService {
    void changeStatus(TaskStatus status, Task model);
}
