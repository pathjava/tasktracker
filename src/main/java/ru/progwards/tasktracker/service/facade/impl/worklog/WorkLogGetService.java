package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.WorkLog;

/**
 * Бизнес-логика получения одного лога
 *
 * @author Oleg Kiselev
 */
@Service
public class WorkLogGetService implements GetService<Long, WorkLog> {

    @Autowired
    private Repository<Long, WorkLogEntity> repository;

    @Autowired
    private Converter<WorkLogEntity, WorkLog> converter;

    /**
     * Метод получения лога по идентификатору
     *
     * @param id идентификатор лога, который необходимо получить
     * @return найденный объект
     */
    @Override
    public WorkLog get(Long id) {
        return id == null ? null : converter.toVo(repository.get(id));
    }
}
