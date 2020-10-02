package repository.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.dao.Repository;
import repository.entity.Task;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TaskRepository implements Repository<Long, Task> {

    private JsonHandlerImpl jsonHandler;

    @Autowired
    public void setJsonHandler(JsonHandlerImpl jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    @Override
    public Collection<Task> get() {
        return jsonHandler.tasks.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Task get(Long id) {
        Task temp = jsonHandler.tasks.get(id);
        if (temp == null)
            throw new IllegalArgumentException(); //TODO определить своё или более подходящее исключение
        return temp;
    }

    @Override
    public void create(Task task) {
        Task temp = jsonHandler.tasks.put(task.getId(), task);
        if (temp == null)
            jsonHandler.write();
    }

    @Override
    public void update(Task task) {
        create(task);
    }

    @Override
    public void delete(Long id) {
        Task temp = jsonHandler.tasks.remove(id);
        if (temp != null)
            jsonHandler.write();
    }

}

