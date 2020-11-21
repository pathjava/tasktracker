package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Бизнес-логика лога (Журнала работ) задачи
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

    @Autowired
    private RefreshService<Task> refreshService;

    @Autowired
    private GetService<Long, Task> taskGetService;

    @Autowired
    private GetService<Long, WorkLog> workLogGetService;

    /**
     * Метод создания лога
     * В момент создания лога у задачи, которой принадлежит лог, происходит увеличение значения
     * затраченного времени, а также изменение оставшегося времени согласно выбранного параметра
     * в пользовательском интерфейсе.
     * AUTO_REDUCE - уменьшение оставшегося времени на getSpent()
     * SET_TO_VALUE - установка произвольного оставшегося времени
     * REDUCE_BY_VALUE - уменьшение оставшегося времени на произвольное значение
     * DONT_CHANGE - не уменьшать оставшееся время
     * INCREASE_BY_VALUE - увеличение оставшегося времени на произвольное значение
     *
     * @param model объект бизнес-логики
     */
    @Override
    public void create(WorkLog model) {
        Task task = taskGetService.get(model.getTaskId());
        task.setTimeSpent(task.getTimeSpent().plus(model.getSpent()));

        switch (model.getEstimateChange()) {
            case AUTO_REDUCE:
                task.setTimeLeft(task.getTimeLeft().minus(model.getSpent()));
                break;
            case SET_TO_VALUE:
                task.setTimeLeft(model.getEstimateValue());
                break;
            case REDUCE_BY_VALUE:
                task.setTimeLeft(task.getTimeLeft().minus(model.getEstimateValue()));
                break;
            case DONT_CHANGE:
            case INCREASE_BY_VALUE:
                break;
        }

        refreshService.refresh(task);
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
     * В момент обновления лога у задачи, которой принадлежит лог, происходит изменение значения
     * затраченного времени, а также изменение оставшегося времени согласно выбранного параметра
     * в пользовательском интерфейсе.
     *
     * @param model value object - объект бизнес логики (задача), который необходимо обновить
     */
    @Override
    public void refresh(WorkLog model) {
        Task task = taskGetService.get(model.getTaskId());
        WorkLog workLog = workLogGetService.get(model.getId());

        task.setTimeSpent(task.getTimeSpent().minus(workLog.getSpent()));
        task.setTimeSpent(task.getTimeSpent().plus(model.getSpent()));

        switch (model.getEstimateChange()) {
            case AUTO_REDUCE:
                task.setTimeLeft(task.getTimeLeft().plus(workLog.getSpent()));
                task.setTimeLeft(task.getTimeLeft().minus(model.getSpent()));
                break;
            case SET_TO_VALUE:
                task.setTimeLeft(model.getEstimateValue());
                break;
            case DONT_CHANGE:
            case REDUCE_BY_VALUE:
            case INCREASE_BY_VALUE:
                break;
        }

        refreshService.refresh(task);
        repository.update(converter.toEntity(model));
    }

    /**
     * Метод удаления лога
     * В момент удаления лога у задачи, которой принадлежит лог, происходит уменьшения значения
     * затраченного времени на значение, ранее установленное в удаляемом логе,
     * а также изменение оставшегося времени согласно выбранного параметра
     * в пользовательском интерфейсе.
     *
     * @param model объект, который необходимо удалить
     */
    @Override
    public void remove(WorkLog model) {
        Task task = taskGetService.get(model.getTaskId());
        WorkLog workLog = workLogGetService.get(model.getId());

        task.setTimeSpent(task.getTimeSpent().minus(workLog.getSpent()));

        switch (model.getEstimateChange()) {
            case AUTO_REDUCE:
                task.setTimeLeft(task.getTimeLeft().minus(workLog.getSpent()));
                break;
            case SET_TO_VALUE:
                task.setTimeLeft(model.getEstimateValue());
                break;
            case INCREASE_BY_VALUE:
                task.setTimeLeft(task.getTimeLeft().plus(model.getEstimateValue()));
                break;
            case DONT_CHANGE:
            case REDUCE_BY_VALUE:
                break;
        }

        refreshService.refresh(task);
        repository.delete(model.getId());
    }
}
