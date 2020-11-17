package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.RefreshService;
import ru.progwards.tasktracker.service.vo.WorkLog;

/**
 * Бизнес-логика обновления лога
 *
 * @author Oleg Kiselev
 */
@Service
public class WorkLogRefreshService implements RefreshService<WorkLog> {

    @Autowired
    private Repository<Long, WorkLogEntity> repository;

    @Autowired
    private Converter<WorkLogEntity, WorkLog> converter;

    /**
     * Метод обновления лога
     *
     * @param model value object - объект бизнес логики (задача), который необходимо обновить
     */
    @Override
    public void refresh(WorkLog model) {
        repository.update(converter.toEntity(model));
    }
}
