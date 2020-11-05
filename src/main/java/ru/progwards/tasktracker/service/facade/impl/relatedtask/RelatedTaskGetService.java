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
     * @param id идентификатор по которому необходимо получить связанную задачу
     * @return найденную связанную задачу или пусто
     */
    @Override
    public RelatedTask get(Long id) {
        return id == null ? null : converter.toVo(repository.get(id));
    }
}
