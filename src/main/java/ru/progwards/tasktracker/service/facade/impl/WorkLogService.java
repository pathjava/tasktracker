package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Бизнес-логика лога задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class WorkLogService implements CreateService<WorkLog>, GetListByTaskService<Long, WorkLog>,
        GetService<Long, WorkLog>, RefreshService<WorkLog>, RemoveService<WorkLog> {

    @Autowired
    private Repository<Long, WorkLogEntity> repository;

    @Autowired
    private RepositoryByTaskId<Long, WorkLogEntity> byTaskId;

    @Autowired
    private Converter<WorkLogEntity, WorkLog> converter;

    /**
     * Метод создания лога
     *
     * @param model объект бизнес-логики
     */
    @Override
    public void create(WorkLog model) {
        repository.create(converter.toEntity(model));
    }

    /**
     * Метод получения коллекции логов по идентификатору задачи
     *
     * @param taskId идентификатор задачи для которой необходимо получить логи
     * @return коллекция объектов лога
     */
    @Override
    public Collection<WorkLog> getListByTaskId(Long taskId) {
        return byTaskId.getByTaskId(taskId).stream()
                .filter(logEntity -> logEntity.getTaskId().equals(taskId))
                .map(logEntity -> converter.toVo(logEntity))
                .collect(Collectors.toList());
    }

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

    /**
     * Метод обновления лога
     *
     * @param model value object - объект бизнес логики (задача), который необходимо обновить
     */
    @Override
    public void refresh(WorkLog model) {
        repository.update(converter.toEntity(model));
    }

    /**
     * Метод удаления лога
     *
     * @param model объект, который необходимо удалить
     */
    @Override
    public void remove(WorkLog model) {
        repository.delete(model.getId());
    }
}
