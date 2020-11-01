package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * удаление задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskRemoveService implements RemoveService<Task> {

    private Repository<Long, TaskEntity> taskRepository;

    @Autowired
    public void setTaskRepository(TaskEntityRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * @param task  value object - объект бизнес логики (задача), который необходимо удалить
     */
    @Override
    public void remove(Task task) {
        taskRepository.delete(task.getId());
    }
}
