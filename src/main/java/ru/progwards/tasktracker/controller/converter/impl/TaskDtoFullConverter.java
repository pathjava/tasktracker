package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.*;
import ru.progwards.tasktracker.service.vo.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskDtoFullConverter implements Converter<Task, TaskDtoFull> {

    @Autowired
    private Converter<TaskType, TaskTypeDto> taskTypeDtoConverter;
    @Autowired
    private Converter<TaskPriority, TaskPriorityDto> taskPriorityDtoConverter;
    @Autowired
    private Converter<User, UserDto> userDtoConverter;
    @Autowired
    private Converter<WorkFlowStatus, WorkFlowStatusDto> workFlowStatusDtoConverter;
    @Autowired
    private Converter<RelatedTask, RelatedTaskDto> relatedTaskDtoConverter;
    @Autowired
    private Converter<TaskAttachment, TaskAttachmentDto> taskAttachmentDtoConverter;
    @Autowired
    private Converter<WorkLog, WorkLogDto> workLogDtoConverter;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public Task toModel(TaskDtoFull dto) {
        if (dto == null)
            return null;
        else
            return new Task(
                    dto.getId(),
                    dto.getCode(),
                    dto.getName(),
                    dto.getDescription(),
                    taskTypeDtoConverter.toModel(dto.getType()),
                    taskPriorityDtoConverter.toModel(dto.getPriority()),
                    dto.getProject_id(),
                    userDtoConverter.toModel(dto.getAuthor()),
                    userDtoConverter.toModel(dto.getExecutor()),
                    dto.getCreated(),
                    dto.getUpdated(),
                    workFlowStatusDtoConverter.toModel(dto.getStatus()),
                    dto.getEstimation(),
                    dto.getTimeSpent(),
                    dto.getTimeLeft(),
                    listDtoToVoRelatedTask(dto.getRelatedTasks()),
                    listDtoToVoTaskAttachment(dto.getAttachments()),
                    listDtoToVoTaskWorkLog(dto.getWorkLogs())
            );
    }

    /**
     * Метод конвертирует лист из Dto в VO
     *
     * @param workLogs лист Dto логов
     * @return лист VO логов
     */
    private List<WorkLog> listDtoToVoTaskWorkLog(List<WorkLogDto> workLogs) {
        return workLogs.stream()
                .map(entity -> workLogDtoConverter.toModel(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из Dto в VO
     *
     * @param attachments лист Dto файлов задачи
     * @return лист VO файлов задачи
     */
    private List<TaskAttachment> listDtoToVoTaskAttachment(List<TaskAttachmentDto> attachments) {
        return attachments.stream()
                .map(entity -> taskAttachmentDtoConverter.toModel(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из Dto в VO
     *
     * @param relatedTasks лист Dto связанных задач
     * @return лист VO связанных задач
     */
    private List<RelatedTask> listDtoToVoRelatedTask(List<RelatedTaskDto> relatedTasks) {
        return relatedTasks.stream()
                .map(entity -> relatedTaskDtoConverter.toModel(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public TaskDtoFull toDto(Task model) {
        if (model == null)
            return null;
        else
            return new TaskDtoFull(
                    model.getId(),
                    model.getCode(),
                    model.getName(),
                    model.getDescription(),
                    taskTypeDtoConverter.toDto(model.getType()),
                    taskPriorityDtoConverter.toDto(model.getPriority()),
                    model.getProject_id(),
                    userDtoConverter.toDto(model.getAuthor()),
                    userDtoConverter.toDto(model.getExecutor()),
                    model.getCreated(),
                    model.getUpdated(),
                    workFlowStatusDtoConverter.toDto(model.getStatus()),
                    model.getEstimation(),
                    model.getTimeSpent(),
                    model.getTimeLeft(),
                    listVoToDtoRelatedTask(model.getRelatedTasks()),
                    listVoToDtoTaskAttachment(model.getAttachments()),
                    listVoToDtoWorkLog(model.getWorkLogs())
            );
    }

    /**
     * Метод конвертирует лист из VO в Dto
     *
     * @param workLogs лист VO логов задачи
     * @return лист Dto логов задачи
     */
    private List<WorkLogDto> listVoToDtoWorkLog(List<WorkLog> workLogs) {
        return workLogs.stream()
                .map(entity -> workLogDtoConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Dto
     *
     * @param attachments лист VO файлов задачи
     * @return лист Dto файлов задачи
     */
    private List<TaskAttachmentDto> listVoToDtoTaskAttachment(List<TaskAttachment> attachments) {
        return attachments.stream()
                .map(entity -> taskAttachmentDtoConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Dto
     *
     * @param relatedTasks лист VO связанных задач
     * @return лист Dto связанных задач
     */
    private List<RelatedTaskDto> listVoToDtoRelatedTask(List<RelatedTask> relatedTasks) {
        return relatedTasks.stream()
                .map(entity -> relatedTaskDtoConverter.toDto(entity))
                .collect(Collectors.toList());
    }
}
