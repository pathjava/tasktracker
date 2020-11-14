package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * Бизнес-логика удаления задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskRemoveService implements RemoveService<Task> {

    private Repository<Long, TaskEntity> repository;

    @Autowired
    public void setRepository(TaskEntityRepository repository) {
        this.repository = repository;
    }

    /**
     * @param model  value object - объект бизнес логики (задача), который необходимо удалить
     */
    @Override
    public void remove(Task model) {
        repository.delete(model.getId());
    }
}
