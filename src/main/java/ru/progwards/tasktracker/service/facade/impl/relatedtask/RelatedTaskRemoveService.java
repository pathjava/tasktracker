package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

/**
 * Бизнес-логика удаления связанной задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class RelatedTaskRemoveService implements RemoveService<RelatedTask> {

    private Repository<Long, RelatedTaskEntity> repository;

    @Autowired
    public void setRepository(Repository<Long, RelatedTaskEntity> repository) {
        this.repository = repository;
    }

    /**
     * метод удаления связанной задачи
     *
     * @param model value object - объект бизнес логики, который необходимо удалить
     */
    @Override
    public void remove(RelatedTask model) {
        repository.delete(model.getId());
    }
}
