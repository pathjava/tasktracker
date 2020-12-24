package ru.progwards.tasktracker.repository.deprecated.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.deprecated.entity.*;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.model.*;

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
@Deprecated
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
    @Autowired
    private Converter<ProjectEntity, Project> projectConverter;
    @Autowired
    private Converter<WorkFlowActionEntity, WorkFlowAction> workFlowActionConverter;
    @Autowired
    private Converter<TaskNoteEntity, TaskNote> taskNoteConverter;

    /**
     * Метод конвертирует сущность Entity в бизнес объект
     *
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public Task toVo(TaskEntity entity) {
//        if (entity == null)
//            return null;
//        else
//            return new Task(
//                    entity.getId(),
//                    entity.getCode(),
//                    entity.getName(),
//                    entity.getDescription(),
//                    taskTypeConverter.toVo(entity.getType()),
//                    taskPriorityConverter.toVo(entity.getPriority()),
//                    projectConverter.toVo(entity.getProject()),
//                    userConverter.toVo(entity.getAuthor()),
//                    userConverter.toVo(entity.getExecutor()),
//                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(entity.getCreated()), ZoneId.of("Europe/Moscow")),
//                    checkUpdatedEntityNotNull(entity.getUpdated()),
//                    workFlowStatusConverter.toVo(entity.getStatus()),
//                    //listEntityToVoWorkFlowAction(entity.getActions()),
//                    checkDurationEntityNotNull(entity.getEstimation()),
//                    checkDurationEntityNotNull(entity.getTimeSpent()),
//                    checkDurationEntityNotNull(entity.getTimeLeft()),
//                    listEntityToVoRelatedTask(entity.getRelatedTasks()),
//                    null, //TODO - check
//                    listEntityToVoTaskAttachment(entity.getAttachments()),
//                    listEntityToVoWorkLog(entity.getWorkLogs()),
//                    listEntityToVoTaskNote(entity.getNotes())
//            );
        return null;
    }

    /**
     * Метод конвертирует лист из Entity в VO
     *
     * @param notes лист Entity TaskNote задачи
     * @return лист VO TaskNote задачи
     */
    private List<TaskNote> listEntityToVoTaskNote(List<TaskNoteEntity> notes) {
        return notes.stream()
                .map(entity -> taskNoteConverter.toVo(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из Entity в VO
     *
     * @param actions лист Entity WorkFlowAction задачи
     * @return лист VO WorkFlowAction задачи
     */
    private List<WorkFlowAction> listEntityToVoWorkFlowAction(List<WorkFlowActionEntity> actions) {
        return actions.stream()
                .map(entity -> workFlowActionConverter.toVo(entity))
                .collect(Collectors.toList());
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
//        if (valueObject == null)
//            return null;
//        else
//            return new TaskEntity(
//                    valueObject.getId(),
//                    valueObject.getCode(),
//                    valueObject.getName(),
//                    valueObject.getDescription(),
//                    taskTypeConverter.toEntity(valueObject.getType()),
//                    taskPriorityConverter.toEntity(valueObject.getPriority()),
//                    projectConverter.toEntity(valueObject.getProject()),
//                    userConverter.toEntity(valueObject.getAuthor()),
//                    userConverter.toEntity(valueObject.getExecutor()),
//                    checkZonedDateTimeValueObjectNotNull(valueObject.getCreated()),
//                    checkZonedDateTimeValueObjectNotNull(valueObject.getUpdated()),
//                    workFlowStatusConverter.toEntity(valueObject.getStatus()),
//                    null,//listVoToEntityWorkFlowAction(valueObject.getActions()),
//                    checkDurationValueObjectNotNull(valueObject.getEstimation()),
//                    checkDurationValueObjectNotNull(valueObject.getTimeSpent()),
//                    checkDurationValueObjectNotNull(valueObject.getTimeLeft()),
//                    listVoToEntityRelatedTask(valueObject.getRelatedTasks()),
//                    listVoToEntityTaskAttachment(valueObject.getAttachments()),
//                    listVoToEntityWorkLog(valueObject.getWorkLogs()),
//                    listVoToEntityTaskNote(valueObject.getNotes()),
//                    false
//            );
        return null;
    }

    /**
     * Метод конвертирует лист из VO в Entity
     *
     * @param notes лист VO TaskNote задачи
     * @return лист Entity TaskNote задачи
     */
    private List<TaskNoteEntity> listVoToEntityTaskNote(List<TaskNote> notes) {
        return notes.stream()
                .map(valueObject -> taskNoteConverter.toEntity(valueObject))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Entity
     *
     * @param actions лист VO WorkFlowAction задачи
     * @return лист Entity WorkFlowAction задачи
     */
    private List<WorkFlowActionEntity> listVoToEntityWorkFlowAction(List<WorkFlowAction> actions) {
        return actions.stream()
                .map(valueObject -> workFlowActionConverter.toEntity(valueObject))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Entity
     *
     * @param workLogs лист VO логов задачи
     * @return лист Entity логов задачи
     */
    private List<WorkLogEntity> listVoToEntityWorkLog(List<WorkLog> workLogs) {
        return workLogs.stream()
                .map(valueObject -> workLogConverter.toEntity(valueObject))
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
                .map(valueObject -> taskAttachmentConverter.toEntity(valueObject))
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
                .map(valueObject -> relatedTaskConverter.toEntity(valueObject))
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
