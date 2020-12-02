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

import java.time.Duration;
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
     *
     * @param model объект бизнес-логики журнала работ
     */
    @Override
    public void create(WorkLog model) {
        Task task = taskGetService.get(model.getTaskId());
        task = logCreateEstimateChange(model, task);
        refreshService.refresh(task);

        WorkLogEntity entity = converter.toEntity(model);
        repository.create(entity);
        model.setId(entity.getId());
    }

    /**
     * В момент создания лога у задачи, которой принадлежит лог, происходит увеличение значения
     * затраченного времени, а также изменение оставшегося времени согласно выбранного параметра
     * в пользовательском интерфейсе.
     * AUTO_REDUCE - уменьшение оставшегося времени на getSpent()
     * SET_TO_VALUE - установка произвольного оставшегося времени
     * REDUCE_BY_VALUE - уменьшение оставшегося времени на произвольное значение
     * DONT_CHANGE - не уменьшать оставшееся время
     * INCREASE_BY_VALUE - увеличение оставшегося времени на произвольное значение
     *
     * @param model объект бизнес-логики журнала работ
     * @param task  задача, к которой принадлежит WorkLog
     *              и у которой выполняется изменение затраченного и оставшегося времени
     */
    public Task logCreateEstimateChange(WorkLog model, Task task) {
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
        return task;
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
     * @param model объект бизнес-логики журнала работ, который необходимо обновить
     */
    @Override
    public void refresh(WorkLog model) {
        Task task = taskGetService.get(model.getTaskId());
        Duration spent = workLogGetService.get(model.getId()).getSpent();
        task = logRefreshEstimateChange(model, spent, task);
        refreshService.refresh(task);

        repository.update(converter.toEntity(model));
    }

    /**
     * В момент обновления лога у задачи, которой принадлежит лог, происходит изменение значения
     * затраченного времени, а также изменение оставшегося времени согласно выбранного параметра
     * в пользовательском интерфейсе.
     *
     * @param model обновляемый объект бизнес-логики журнала работ
     * @param spent ранее затраченное время журнала работ
     * @param task  задача, к которой принадлежит WorkLog
     */
    public Task logRefreshEstimateChange(WorkLog model, Duration spent, Task task) {
        task.setTimeSpent(task.getTimeSpent().minus(spent));
        task.setTimeSpent(task.getTimeSpent().plus(model.getSpent()));

        switch (model.getEstimateChange()) {
            case AUTO_REDUCE:
                task.setTimeLeft(task.getTimeLeft().plus(spent));
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
        return task;
    }

    /**
     * Метод удаления лога
     *
     * @param model объект бизнес-логики журнала работ, который необходимо удалить
     */
    @Override
    public void remove(WorkLog model) {
        Task task = taskGetService.get(model.getTaskId());
        Duration spent = workLogGetService.get(model.getId()).getSpent();
        task = logRemoveEstimateChange(model, spent, task);
        refreshService.refresh(task);

        repository.delete(model.getId());
    }

    /**
     * В момент удаления лога у задачи, которой принадлежит лог, происходит уменьшения значения
     * затраченного времени на значение, ранее установленное в удаляемом логе,
     * а также изменение оставшегося времени согласно выбранного параметра
     * в пользовательском интерфейсе.
     *
     * @param model удаляемый объект бизнес-логики журнала работ
     * @param spent ранее затраченное время журнала работ
     * @param task  задача, к которой принадлежит WorkLog
     */
    public Task logRemoveEstimateChange(WorkLog model, Duration spent, Task task) {
        task.setTimeSpent(task.getTimeSpent().minus(spent));

        switch (model.getEstimateChange()) {
            case AUTO_REDUCE:
                task.setTimeLeft(task.getTimeLeft().minus(spent));
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
        return task;
    }
}
