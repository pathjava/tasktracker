package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.model.WorkLog;
import ru.progwards.tasktracker.model.types.EstimateChange;
import ru.progwards.tasktracker.repository.TaskRepository;
import ru.progwards.tasktracker.repository.WorkLogRepository;
import ru.progwards.tasktracker.service.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

import static java.lang.String.format;

/**
 * Бизнес-логика лога (Журнала работ) задачи
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkLogService implements CreateService<WorkLog>, GetService<Long, WorkLog>,
        RefreshService<WorkLog>, RemoveService<WorkLog>, GetListService<WorkLog> {

    private final WorkLogRepository workLogRepository;
    private final TaskRepository taskRepository;

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

        Duration taskSpent = getTimeDuration(task.getTimeSpent());
        Duration taskLeft = getTimeDuration(task.getTimeLeft());
        Duration workLogSpent = getTimeDuration(model.getSpent());
        Duration estimateValue = getTimeDuration(model.getEstimateValue());

        task.setTimeSpent(taskSpent.plus(workLogSpent));

        switch (model.getEstimateChange()) {
            case AUTO_REDUCE:
                task.setTimeLeft(taskLeft.minus(workLogSpent));
                break;
            case SET_TO_VALUE:
                task.setTimeLeft(estimateValue);
                break;
            case REDUCE_BY_VALUE:
                task.setTimeLeft(taskLeft.minus(estimateValue));
                break;
            case DONT_CHANGE:
            case INCREASE_BY_VALUE:
                break;
        }
        taskRepository.save(task);
    }

    /**
     * Метод проверки Duration на null
     *
     * @param duration Duration
     * @return Duration, если не null в параметре и Duration.ZERO если null
     */
    private Duration getTimeDuration(Duration duration) {
        return duration != null ? duration : Duration.ZERO;
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
                .orElseThrow(() -> new NotFoundException(format("WorkLog id=%s not found", id)));
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

        Duration taskSpent = getTimeDuration(task.getTimeSpent());
        Duration taskLeft = getTimeDuration(task.getTimeLeft());
        Duration workLogSpent = getTimeDuration(model.getSpent());
        Duration estimateValue = getTimeDuration(model.getEstimateValue());

        task.setTimeSpent(taskSpent.minus(workLog.getSpent()));
        task.setTimeSpent(taskSpent.plus(workLogSpent));

        switch (model.getEstimateChange()) {
            case AUTO_REDUCE:
                task.setTimeLeft(taskLeft.plus(workLog.getSpent()));
                task.setTimeLeft(taskLeft.minus(workLogSpent));
                break;
            case SET_TO_VALUE:
                task.setTimeLeft(estimateValue);
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
                .orElseThrow(() -> new NotFoundException(format("Task id=%s not found", id)));
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

        Duration taskSpent = getTimeDuration(task.getTimeSpent());
        Duration taskLeft = getTimeDuration(task.getTimeLeft());
        Duration estimateValue = getTimeDuration(model.getEstimateValue());

        task.setTimeSpent(taskSpent.minus(workLog.getSpent()));

        switch (model.getEstimateChange()) {
            case AUTO_REDUCE:
                task.setTimeLeft(taskLeft.minus(workLog.getSpent()));
                break;
            case SET_TO_VALUE:
                task.setTimeLeft(estimateValue);
                break;
            case INCREASE_BY_VALUE:
                task.setTimeLeft(taskLeft.plus(estimateValue));
                break;
            case DONT_CHANGE:
            case REDUCE_BY_VALUE:
                break;
        }
        taskRepository.save(task);
    }

    /**
     * Метод получения полного списка журнала работ WorkLog
     *
     * @return список WorkLog
     */
    @Override
    public List<WorkLog> getList() {
        return workLogRepository.findAll();
    }

    /**
     * Метод создания WorkLog по шаблону
     *
     * @param args [0] - массив Task[] (минимум 2 Task), [1] - User
     */
    /*@Transactional
    @Override
    public List<WorkLog> createFromTemplate(Object... args) {
        if (args.length != 2)
            throw new OperationIsNotPossibleException("WorkLog.createFromTemplate: 2 arguments expected");
        if (!(args[0] instanceof List))
            throw new OperationIsNotPossibleException("WorkLog.createFromTemplate: argument 0 must be List<Task>");
        if (!(args[1] instanceof User))
            throw new OperationIsNotPossibleException("WorkLog.createFromTemplate: argument 1 must be User");

        // passing the list of tasks
        @SuppressWarnings("unchecked")
        List<Task> tasks = (List<Task>) args[0];
        if (tasks.size() < 2)
            throw new OperationIsNotPossibleException("WorkLog.createFromTemplate: List<Task> min size() expected 2");

        for (Task task : tasks) {
            WorkLog workLog = new WorkLog();
            workLog.setTask(task);
            workLog.setWorker((User) args[1]);
            workLog.setSpent(Duration.ofHours(5));
            workLog.setStart(ZonedDateTime.now());
            workLog.setDescription("Some description WorkLog for Task: " + task.getName());
            workLog.setEstimateChange(EstimateChange.DONT_CHANGE);

            workLogRepository.save(workLog);
        }
    }*/
}
