package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.WorkLog;

/**
 * Бизнес-логика создания лога
 *
 * @author Oleg Kiselev
 */
@Service
public class WorkLogCreateService implements CreateService<WorkLog> {

    private Repository<Long, WorkLogEntity> repository;
    private Converter<WorkLogEntity, WorkLog> converter;

    @Autowired
    public void setRepository(Repository<Long, WorkLogEntity> repository) {
        this.repository = repository;
    }

    @Autowired
    public void setConverter(Converter<WorkLogEntity, WorkLog> converter) {
        this.converter = converter;
    }

    /**
     * @param model объект бизнес-логики
     */
    @Override
    public void create(WorkLog model) {
        repository.create(converter.toEntity(model));
    }
}
