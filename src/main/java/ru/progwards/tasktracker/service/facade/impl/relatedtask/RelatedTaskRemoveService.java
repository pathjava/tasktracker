package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

@Service
public class RelatedTaskRemoveService implements RemoveService<RelatedTask> {

    private Repository<Long, RelatedTaskEntity> entityRepository;

    @Autowired
    public void setEntityRepository(Repository<Long, RelatedTaskEntity> entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Override
    public void remove(RelatedTask model) {
        entityRepository.delete(model.getId());
    }
}
