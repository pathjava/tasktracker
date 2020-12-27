package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.WorkLog;
import ru.progwards.tasktracker.repository.TaskRepository;
import ru.progwards.tasktracker.repository.WorkLogRepository;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RefreshService;
import ru.progwards.tasktracker.service.RemoveService;

/**
 * Бизнес-логика лога (Журнала работ) задачи
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkLogService implements CreateService<WorkLog>, GetService<Long, WorkLog>,
        RefreshService<WorkLog>, RemoveService<WorkLog> {

    private final @NonNull WorkLogRepository workLogRepository;
    private final @NonNull TaskRepository taskRepository;

    /**
     * Метод создания лога
     *
     * @param model объект бизнес-логики журнала работ
     */
    @Transactional
    @Override
    public void create(WorkLog model) {
        logCreateEstimateChange(model);

        workLogRepository.save(model);
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
     */
    @Transactional
    public void logCreateEstimateChange(WorkLog model) {
        Task task = getTask(model.getTask().getId());

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
        taskRepository.save(task);
    }

    /**
     * Метод получения лога по идентификатору
     *
     * @param id идентификатор лога, который необходимо получить
     * @return найденный объект
     */
    @Override
    public WorkLog get(Long id) {
        return workLogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("WorkLog id=" + id + " not found"));
    }

    /**
     * Метод обновления лога
     *
     * @param model объект бизнес-логики журнала работ, который необходимо обновить
     */
    @Transactional
    @Override
    public void refresh(WorkLog model) {
        logRefreshEstimateChange(model);

        workLogRepository.save(model);
    }

    /**
     * В момент обновления лога у задачи, которой принадлежит лог, происходит изменение значения
     * затраченного времени, а также изменение оставшегося времени согласно выбранного параметра
     * в пользовательском интерфейсе.
     *
     * @param model обновляемый объект бизнес-логики журнала работ
     */
    @Transactional
    public void logRefreshEstimateChange(WorkLog model) {
        Task task = getTask(model.getTask().getId());
        WorkLog workLog = get(model.getId());

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
        taskRepository.save(task);
    }

    /**
     * Метод получения задачи по идентификатору
     *
     * @param id идентификатор Task
     * @return полученный Task
     */
    private Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task id=" + id + " not found"));
    }

    /**
     * Метод удаления лога
     *
     * @param model объект бизнес-логики журнала работ, который необходимо удалить
     */
    @Transactional
    @Override
    public void remove(WorkLog model) {
        logRemoveEstimateChange(model);

        workLogRepository.delete(model);
    }

    /**
     * В момент удаления лога у задачи, которой принадлежит лог, происходит уменьшения значения
     * затраченного времени на значение, ранее установленное в удаляемом логе,
     * а также изменение оставшегося времени согласно выбранного параметра
     * в пользовательском интерфейсе.
     *
     * @param model удаляемый объект бизнес-логики журнала работ
     */
    @Transactional
    public void logRemoveEstimateChange(WorkLog model) {
        Task task = getTask(model.getTask().getId());
        WorkLog workLog = get(model.getId());

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
        taskRepository.save(task);
    }
}
