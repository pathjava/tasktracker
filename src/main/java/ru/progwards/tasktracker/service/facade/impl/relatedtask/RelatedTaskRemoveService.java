package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.RelatedTaskEntityRepository;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

@Service
public class RelatedTaskRemoveService implements RemoveService<RelatedTask> {

    private RelatedTaskEntityRepository entityRepository;

    @Autowired
    public void setEntityRepository(RelatedTaskEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Override
    public void remove(RelatedTask model) {
        entityRepository.delete(model.getId());
    }
}
