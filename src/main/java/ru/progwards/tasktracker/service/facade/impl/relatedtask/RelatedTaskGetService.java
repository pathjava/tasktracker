package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

/**
 * Бизнес-логика получения связанной задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class RelatedTaskGetService implements GetService<Long, RelatedTask> {

    @Autowired
    private Repository<Long, RelatedTaskEntity> repository;

    @Autowired
    private Converter<RelatedTaskEntity, RelatedTask> converter;

    /**
     * Метод получения связанной задачи по её идентификатору
     *
     * @param id идентификатор по которому необходимо получить связанную задачу
     * @return найденную связанную задачу или пусто
     */
    @Override
    public RelatedTask get(Long id) {
        return id == null ? null : converter.toVo(repository.get(id));
    }
}
