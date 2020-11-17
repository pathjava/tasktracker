package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;

/**
 * Бизнес-логика удаления задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskRemoveService implements RemoveService<Task> {

    @Autowired
    private Repository<Long, TaskEntity> repository;

    @Autowired
    private GetListByTaskService<Long, RelatedTask> listByTaskService;

    @Autowired
    private RemoveService<RelatedTask> removeService;

    /**
     * Метод удаления задачи
     *
     * @param model value object - объект бизнес логики (задача), который необходимо удалить
     */
    @Override
    public void remove(Task model) {
        deleteRelatedTasks(model.getId());
        repository.delete(model.getId());
    }

    /**
     * Метод удаления связанных задача удаляемой задачи
     *
     * @param id идентификатор задачи у которой необходимо удалить связанные с ней задачи
     */
    private void deleteRelatedTasks(Long id) {
        Collection<RelatedTask> collection = listByTaskService.getListByTaskId(id);
        if (!collection.isEmpty()) {
            for (RelatedTask relatedTask : collection) {
                removeService.remove(relatedTask);
            }
        }
    }
}
