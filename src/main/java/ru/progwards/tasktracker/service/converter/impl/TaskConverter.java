package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.*;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.*;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Конвертеры valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskConverter implements Converter<TaskEntity, Task> {

    @Autowired
    private Converter<TaskTypeEntity, TaskType> taskTypeConverter;
    @Autowired
    private Converter<TaskPriorityEntity, TaskPriority> taskPriorityConverter;
    @Autowired
    private Converter<UserEntity, User> userConverter;
    @Autowired
    private Converter<WorkFlowStatusEntity, WorkFlowStatus> workFlowStatusConverter;
    @Autowired
    private Converter<RelatedTaskEntity, RelatedTask> relatedTaskConverter;
    @Autowired
    private Converter<TaskAttachmentEntity, TaskAttachment> taskAttachmentConverter;
    @Autowired
    private Converter<WorkLogEntity, WorkLog> workLogConverter;

    /**
     * Метод конвертирует сущность Entity в бизнес объект
     *
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public Task toVo(TaskEntity entity) {
        if (entity == null)
            return null;
        else
            return new Task(
                    entity.getId(),
                    entity.getCode(),
                    entity.getName(),
                    entity.getDescription(),
                    taskTypeConverter.toVo(entity.getType()),
                    taskPriorityConverter.toVo(entity.getPriority()),
                    entity.getProject_id(),
                    userConverter.toVo(entity.getAuthor()),
                    userConverter.toVo(entity.getExecutor()),
                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(entity.getCreated()), ZoneId.of("Europe/Moscow")),
                    checkUpdatedEntityNotNull(entity.getUpdated()),
                    workFlowStatusConverter.toVo(entity.getStatus()),
                    checkDurationEntityNotNull(entity.getEstimation()),
                    checkDurationEntityNotNull(entity.getTimeSpent()),
                    checkDurationEntityNotNull(entity.getTimeLeft()),
                    listEntityToVoRelatedTask(entity.getRelatedTasks()),
                    listEntityToVoTaskAttachment(entity.getAttachments()),
                    listEntityToVoWorkLog(entity.getWorkLogs())
            );
    }

    /**
     * Метод конвертирует лист из Entity в VO
     *
     * @param workLogs лист Entity логов задачи
     * @return лист VO логов задачи
     */
    private List<WorkLog> listEntityToVoWorkLog(List<WorkLogEntity> workLogs) {
        return workLogs.stream()
                .map(entity -> workLogConverter.toVo(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из Entity в VO
     *
     * @param attachments лист Entity файлов задачи
     * @return лист VO файлов задачи
     */
    private List<TaskAttachment> listEntityToVoTaskAttachment(List<TaskAttachmentEntity> attachments) {
        return attachments.stream()
                .map(entity -> taskAttachmentConverter.toVo(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из Entity в VO
     *
     * @param relatedTasks лист Entity связанных задач
     * @return лист VO связанных задач
     */
    private List<RelatedTask> listEntityToVoRelatedTask(List<RelatedTaskEntity> relatedTasks) {
        return relatedTasks.stream()
                .map(entity -> relatedTaskConverter.toVo(entity))
                .collect(Collectors.toList());
    }

    /**
     * @param duration секунды, могут быть пустыми и значение
     * @return продолжительность или пусто
     */
    private Duration checkDurationEntityNotNull(Long duration) {
        return duration != null ? Duration.ofSeconds(duration) : null;
    }

    /**
     * @param updated секунды, могут быть пустыми и значение
     * @return дату-время или пусто
     */
    private ZonedDateTime checkUpdatedEntityNotNull(Long updated) {
        return updated != null ? ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(updated), ZoneId.of("Europe/Moscow")) : null;
    }

    /**
     * Метод конвертирует бизнес объект в сущность Entity
     *
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public TaskEntity toEntity(Task valueObject) {
        if (valueObject == null)
            return null;
        else
            return new TaskEntity(
                    valueObject.getId(),
                    valueObject.getCode(),
                    valueObject.getName(),
                    valueObject.getDescription(),
                    taskTypeConverter.toEntity(valueObject.getType()),
                    taskPriorityConverter.toEntity(valueObject.getPriority()),
                    valueObject.getProject_id(),
                    userConverter.toEntity(valueObject.getAuthor()),
                    userConverter.toEntity(valueObject.getExecutor()),
                    checkZonedDateTimeValueObjectNotNull(valueObject.getCreated()),
                    checkZonedDateTimeValueObjectNotNull(valueObject.getUpdated()),
                    workFlowStatusConverter.toEntity(valueObject.getStatus()),
                    checkDurationValueObjectNotNull(valueObject.getEstimation()),
                    checkDurationValueObjectNotNull(valueObject.getTimeSpent()),
                    checkDurationValueObjectNotNull(valueObject.getTimeLeft()),
                    listVoToEntityRelatedTask(valueObject.getRelatedTasks()),
                    listVoToEntityTaskAttachment(valueObject.getAttachments()),
                    listVoToEntityWorkLog(valueObject.getWorkLogs()),
                    false
            );
    }

    /**
     * Метод конвертирует лист из VO в Entity
     *
     * @param workLogs лист VO логов задачи
     * @return лист Entity логов задачи
     */
    private List<WorkLogEntity> listVoToEntityWorkLog(List<WorkLog> workLogs) {
        return workLogs.stream()
                .map(entity -> workLogConverter.toEntity(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Entity
     *
     * @param attachments лист VO файлов задачи
     * @return лист Entity файлов задачи
     */
    private List<TaskAttachmentEntity> listVoToEntityTaskAttachment(List<TaskAttachment> attachments) {
        return attachments.stream()
                .map(entity -> taskAttachmentConverter.toEntity(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Entity
     *
     * @param relatedTasks лист VO связанных задач
     * @return лист Entity связанных задач
     */
    private List<RelatedTaskEntity> listVoToEntityRelatedTask(List<RelatedTask> relatedTasks) {
        return relatedTasks.stream()
                .map(entity -> relatedTaskConverter.toEntity(entity))
                .collect(Collectors.toList());
    }

    /**
     * @param duration продолжительность
     * @return продолжительность в секундах или пусто
     */
    private Long checkDurationValueObjectNotNull(Duration duration) {
        return duration != null ? duration.toSeconds() : null;
    }

    /**
     * @param time дата-время
     * @return дата-время в секундах или пусто
     */
    private Long checkZonedDateTimeValueObjectNotNull(ZonedDateTime time) {
        return time != null ? time.toEpochSecond() : null;
    }
}
