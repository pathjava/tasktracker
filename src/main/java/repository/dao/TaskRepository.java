package repository.dao;

import repository.entity.Task;

public interface TaskRepository extends Repository<Long, Task> {
    void save(Task task);
}
