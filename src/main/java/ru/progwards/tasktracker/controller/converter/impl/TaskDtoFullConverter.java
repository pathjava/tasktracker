package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.*;
import ru.progwards.tasktracker.service.facade.GetService;
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
    private Converter<TaskType, TaskTypeDtoPreview> taskTypeDtoConverter;
    @Autowired
    private Converter<TaskPriority, TaskPriorityDtoPreview> taskPriorityDtoConverter;
    @Autowired
    private Converter<User, UserDtoPreview> userDtoConverter;
    @Autowired
    private Converter<WorkFlowStatus, WorkFlowStatusDtoPreview> workFlowStatusDtoConverter;
    @Autowired
    private Converter<RelatedTask, RelatedTaskDtoPreview> relatedTaskDtoConverter;
    @Autowired
    private Converter<TaskAttachment, TaskAttachmentDtoPreview> taskAttachmentDtoConverter;
    @Autowired
    private Converter<WorkFlowAction, WorkFlowActionDtoPreview> workFlowActionDtoConverter;
    @Autowired
    private Converter<Project, ProjectDtoPreview> projectDtoConverter;
    @Autowired
    private GetService<Long, Task> taskGetService;

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
        else {
            Task task = taskGetService.get(dto.getId());
            return new Task(
                    dto.getId(),
                    dto.getCode(),
                    dto.getName(),
                    dto.getDescription(),
                    taskTypeDtoConverter.toModel(dto.getType()),
                    taskPriorityDtoConverter.toModel(dto.getPriority()),
                    projectDtoConverter.toModel(dto.getProject()),
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
                    task.getWorkLogs(),
                    task.getNotes()
            );
        }
    }

    /**
     * Метод конвертирует лист из Dto в VO
     *
     * @param actions лист Dto WorkFlowAction
     * @return лист VO WorkFlowAction
     */
    private List<WorkFlowAction> listDtoToVoWorkFlowAction(List<WorkFlowActionDtoPreview> actions) {
        return actions.stream()
                .map(dto -> workFlowActionDtoConverter.toModel(dto))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из Dto в VO
     *
     * @param attachments лист Dto файлов задачи
     * @return лист VO файлов задачи
     */
    private List<TaskAttachment> listDtoToVoTaskAttachment(List<TaskAttachmentDtoPreview> attachments) {
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
    private List<RelatedTask> listDtoToVoRelatedTask(List<RelatedTaskDtoPreview> relatedTasks) {
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
                    taskPriorityDtoConverter.toDto(model.getPriority()),
                    projectDtoConverter.toDto(model.getProject()),
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
                    listVoToDtoTaskAttachment(model.getAttachments())
            );
    }

    /**
     * Метод конвертирует лист из VO в Dto
     *
     * @param actions лист VO WorkFlowAction задачи
     * @return лист Dto WorkFlowAction задачи
     */
    private List<WorkFlowActionDtoPreview> listVoToDtoWorkFlowAction(List<WorkFlowAction> actions) {
        return actions.stream()
                .map(model -> workFlowActionDtoConverter.toDto(model))
                .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Dto
     *
     * @param attachments лист VO файлов задачи
     * @return лист Dto файлов задачи
     */
    private List<TaskAttachmentDtoPreview> listVoToDtoTaskAttachment(List<TaskAttachment> attachments) {
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
    private List<RelatedTaskDtoPreview> listVoToDtoRelatedTask(List<RelatedTask> relatedTasks) {
        return relatedTasks.stream()
                .map(model -> relatedTaskDtoConverter.toDto(model))
                .collect(Collectors.toList());
    }
}
