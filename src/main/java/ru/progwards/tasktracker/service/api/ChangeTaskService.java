package ru.progwards.tasktracker.service.api;

import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.util.types.Status;

public interface ChangeTaskService {
    void changeStatus(Status status, Task task);
}
