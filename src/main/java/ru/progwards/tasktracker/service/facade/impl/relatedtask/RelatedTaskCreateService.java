package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

import java.util.Random;

/**
 * Бизнес-логика создания связанной задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class RelatedTaskCreateService implements CreateService<RelatedTask> {

    private Repository<Long, RelatedTaskEntity> repository;
    private Converter<RelatedTaskEntity, RelatedTask> converter;

    @Autowired
    public void setRepository(Repository<Long, RelatedTaskEntity> repository) {
        this.repository = repository;
    }

    @Autowired
    public void setConverter(Converter<RelatedTaskEntity, RelatedTask> converter) {
        this.converter = converter;
    }

    /**
     * метод создания связанной задачи
     *
     * @param model value object - объект бизнес логики, который необходимо создать
     */
    @Override
    public void create(RelatedTask model) {
        if (model.getId() == null) //TODO - for testing generate id
            model.setId(new Random().nextLong());
        repository.create(converter.toEntity(model));
    }
}
