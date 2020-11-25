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
    private Converter<TaskType, TaskTypeDtoFull> taskTypeDtoConverter;
//    @Autowired
//    private Converter<TaskPriority, TaskPriorityDtoPreview> taskPriorityDtoConverter;
    @Autowired
    private Converter<User, UserDto> userDtoConverter;
    @Autowired
    private Converter<WorkFlowStatus, WorkFlowStatusDto> workFlowStatusDtoConverter;
    @Autowired
    private Converter<RelatedTask, RelatedTaskDtoFull> relatedTaskDtoConverter;
    @Autowired
    private Converter<TaskAttachment, TaskAttachmentDto> taskAttachmentDtoConverter;
    @Autowired
    private Converter<WorkLog, WorkLogDtoFull> workLogDtoConverter;
    @Autowired
    private Converter<WorkFlowAction, WorkFlowActionDto> workFlowActionDtoConverter;

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
                    null,
                    //taskPriorityDtoConverter.toModel(dto.getPriority()),
                    dto.getProject(),
                    userDtoConverter.toModel(dto.getAuthor()),
                    userDtoConverter.toModel(dto.getExecutor()),
                    dto.getCreated(),
                    dto.getUpdated(),
                    workFlowStatusDtoConverter.toModel(dto.getStatus()),
                    listDtoToVoWorkFlowAction(dto.getActions()),
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
     * @param actions лист Dto WorkFlowAction
     * @return лист VO WorkFlowAction
     */
    private List<WorkFlowAction> listDtoToVoWorkFlowAction(List<WorkFlowActionDto> actions) {
        return actions.stream()
                .map(dto -> workFlowActionDtoConverter.toModel(dto))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из Dto в VO
     *
     * @param workLogs лист Dto логов
     * @return лист VO логов
     */
    private List<WorkLog> listDtoToVoTaskWorkLog(List<WorkLogDtoFull> workLogs) {
        return workLogs.stream()
                .map(dto -> workLogDtoConverter.toModel(dto))
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
                .map(dto -> taskAttachmentDtoConverter.toModel(dto))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из Dto в VO
     *
     * @param relatedTasks лист Dto связанных задач
     * @return лист VO связанных задач
     */
    private List<RelatedTask> listDtoToVoRelatedTask(List<RelatedTaskDtoFull> relatedTasks) {
        return relatedTasks.stream()
                .map(dto -> relatedTaskDtoConverter.toModel(dto))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return dto, возвращаемый в пользовательский интерфейс
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
                    null,//taskPriorityDtoConverter.toDto(model.getPriority()),
                    model.getProject(),
                    userDtoConverter.toDto(model.getAuthor()),
                    userDtoConverter.toDto(model.getExecutor()),
                    model.getCreated(),
                    model.getUpdated(),
                    workFlowStatusDtoConverter.toDto(model.getStatus()),
                    listVoToDtoWorkFlowAction(model.getActions()),
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
     * @param actions лист VO WorkFlowAction задачи
     * @return лист Dto WorkFlowAction задачи
     */
    private List<WorkFlowActionDto> listVoToDtoWorkFlowAction(List<WorkFlowAction> actions) {
        return actions.stream()
                .map(model -> workFlowActionDtoConverter.toDto(model))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Dto
     *
     * @param workLogs лист VO логов задачи
     * @return лист Dto логов задачи
     */
    private List<WorkLogDtoFull> listVoToDtoWorkLog(List<WorkLog> workLogs) {
        return workLogs.stream()
                .map(model -> workLogDtoConverter.toDto(model))
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
                .map(model -> taskAttachmentDtoConverter.toDto(model))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Dto
     *
     * @param relatedTasks лист VO связанных задач
     * @return лист Dto связанных задач
     */
    private List<RelatedTaskDtoFull> listVoToDtoRelatedTask(List<RelatedTask> relatedTasks) {
        return relatedTasks.stream()
                .map(model -> relatedTaskDtoConverter.toDto(model))
                .collect(Collectors.toList());
    }
}
