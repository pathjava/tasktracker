package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Бизнес-логика получения списка логов задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class WorkLogGetListByTaskService implements GetListByTaskService<Long, WorkLog> {

    @Autowired
    private RepositoryByTaskId<Long, WorkLogEntity> repository;

    @Autowired
    private Converter<WorkLogEntity, WorkLog> converter;

    /**
     * Метод получения коллекции логов по идентификатору задачи
     *
     * @param taskId идентификатор задачи для которой необходимо получить логи
     * @return коллекция объектов лога
     */
    @Override
    public Collection<WorkLog> getListByTaskId(Long taskId) {
        return repository.getByTaskId(taskId).stream()
                .filter(logEntity -> logEntity.getTaskId().equals(taskId))
                .map(logEntity -> converter.toVo(logEntity))
                .collect(Collectors.toList());
    }
}
