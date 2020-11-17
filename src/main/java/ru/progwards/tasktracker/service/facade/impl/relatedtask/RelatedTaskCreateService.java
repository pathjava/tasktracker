package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

/**
 * Бизнес-логика создания связанной задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class RelatedTaskCreateService implements CreateService<RelatedTask> {

    @Autowired
    private Repository<Long, RelatedTaskEntity> repository;

    @Autowired
    private Converter<RelatedTaskEntity, RelatedTask> converter;

    /**
     * Метод создания связанной задачи
     *
     * @param model value object - объект бизнес логики, который необходимо создать
     */
    @Override
    public void create(RelatedTask model) {
        repository.create(converter.toEntity(model));
    }
}
