package ru.progwards.service.api;

import ru.progwards.service.vo.Task;
import ru.progwards.util.types.Status;

public interface ChangeTaskService {
    void changeStatus(Status status, Task task);
}
