package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.facade.GetListByAttachedTaskId;
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
    private Repository<Long, TaskEntity> repositoryTask;
    @Autowired
    private Repository<Long, RelatedTaskEntity> repositoryRelatedTask;
    @Autowired
    private GetListByAttachedTaskId<Long, RelatedTask> listByAttachedTaskId;

    /**
     * Метод удаления задачи
     * Предварительно в методе deleteRelatedTasks(Long id) проверяем наличие
     * связей на задачи и если они есть, удаляем встречные (входящие)
     *
     * @param model value object - объект бизнес логики (задача), который необходимо удалить
     */
    @Override
    public void remove(Task model) {
        deleteRelatedTasksBeforeRemoveTask(model.getId());
        repositoryTask.delete(model.getId());
    }

    /**
     * Метод удаления связанных входящих RelatedTask удаляемой задачи Task
     *
     * @param id идентификатор задачи
     */
    private void deleteRelatedTasksBeforeRemoveTask(Long id) {
        Collection<RelatedTask> collection = listByAttachedTaskId.getListByAttachedTaskId(id);
        if (!collection.isEmpty()){
            collection.forEach(relatedTask -> repositoryRelatedTask.delete(relatedTask.getId()));
        }
    }
}
