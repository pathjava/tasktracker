package repository.dao.impl;


import repository.dao.Repository;
import repository.entity.Task;

import java.util.Collection;
import java.util.stream.Collectors;

public class TaskRepository implements Repository<Task, Task> {

    private JsonHandlerImpl jsonHandler;

    @Override
    public Collection<Task> get() {
        return jsonHandler.tasks.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Task get(Task id) {
        Task temp = jsonHandler.tasks.get(id.getId());
        if (temp == null)
            throw new IllegalArgumentException();
        return temp;
    }

    @Override
    public void create(Task id) {
    }

    @Override
    public void update(Task id) {

    }

    @Override
    public void delete(Task id) {
        Task temp = jsonHandler.tasks.remove(id.getId());
        if (temp != null)
            jsonHandler.write();
    }

}

