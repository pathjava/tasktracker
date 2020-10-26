package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.RelatedTaskEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.RelatedTaskConverter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

@Service
public class RelatedTaskCreateService implements CreateService<RelatedTask> {

    private RelatedTaskEntityRepository entityRepository;
    private RelatedTaskConverter taskConverter;

    @Autowired
    public void setEntityRepository(RelatedTaskEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Autowired
    public void setTaskConverter(RelatedTaskConverter taskConverter) {
        this.taskConverter = taskConverter;
    }

    @Override
    public void create(RelatedTask model) {
        entityRepository.create(taskConverter.toEntity(model));
    }
}
