package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

@Service
public class RelatedTaskCreateService implements CreateService<RelatedTask> {

    private Repository<Long, RelatedTaskEntity> entityRepository;
    private Converter<RelatedTaskEntity, RelatedTask> taskConverter;

    @Autowired
    public void setEntityRepository(Repository<Long, RelatedTaskEntity> entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Autowired
    public void setTaskConverter(Converter<RelatedTaskEntity, RelatedTask> taskConverter) {
        this.taskConverter = taskConverter;
    }

    @Override
    public void create(RelatedTask model) {
        entityRepository.create(taskConverter.toEntity(model));
    }
}
