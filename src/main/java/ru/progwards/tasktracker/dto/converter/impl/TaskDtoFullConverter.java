package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.service.GetService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskDtoFullConverter implements Converter<Task, TaskDtoFull> {

    private final Converter<TaskType, TaskTypeDtoPreview> taskTypeDtoConverter;
    private final Converter<TaskPriority, TaskPriorityDtoPreview> taskPriorityDtoConverter;
    private final Converter<User, UserDtoPreview> userDtoConverter;
    private final Converter<WorkFlowStatus, WorkFlowStatusDtoPreview> workFlowStatusDtoConverter;
    private final Converter<RelatedTask, RelatedTaskDtoPreview> relatedTaskDtoConverter;
    private final Converter<TaskAttachment, TaskAttachmentDtoPreview> taskAttachmentDtoConverter;
    private final Converter<WorkFlowAction, WorkFlowActionDtoPreview> workFlowActionDtoConverter;
    private final Converter<Project, ProjectDtoPreview> projectDtoConverter;
    private final GetService<Long, Task> taskGetService;

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
        else if (dto.getId() == null) {
            return new Task(
                    null,
                    null,
                    dto.getName().trim(),
                    dto.getDescription(),
                    taskTypeDtoConverter.toModel(dto.getType()),
                    checkTaskPriorityDto(dto.getPriority()),
                    projectDtoConverter.toModel(dto.getProject()),
                    userDtoConverter.toModel(dto.getAuthor()),
                    checkUserDto(dto.getExecutor()),
                    null,
                    null,
                    checkWorkFlowStatusDto(dto.getStatus()),
                    dto.getEstimation(),
                    dto.getTimeSpent(),
                    dto.getTimeLeft(),
                    listDtoToVoRelatedTask(dto.getRelatedTasks()),
                    Collections.emptyList(),
                    listDtoToVoTaskAttachment(dto.getAttachments()),
                    Collections.emptyList(),
                    Collections.emptyList(),
                    false
            );
        } else {
            Task task = taskGetService.get(dto.getId());
            task.setCode(dto.getCode().trim());
            task.setName(dto.getName().trim());
            task.setDescription(dto.getDescription());
            task.setType(taskTypeDtoConverter.toModel(dto.getType()));
            task.setPriority(checkTaskPriorityDto(dto.getPriority()));
            task.setProject(projectDtoConverter.toModel(dto.getProject()));
            task.setAuthor(userDtoConverter.toModel(dto.getAuthor()));
            task.setExecutor(checkUserDto(dto.getExecutor()));
            task.setStatus(checkWorkFlowStatusDto(dto.getStatus()));
            task.setEstimation(dto.getEstimation());
            task.setTimeSpent(dto.getTimeSpent());
            task.setTimeLeft(dto.getTimeLeft());
            task.setRelatedTasks(listDtoToVoRelatedTask(dto.getRelatedTasks()));
            task.setAttachments(listDtoToVoTaskAttachment(dto.getAttachments()));
            return task;
        }
    }

    /**
     * Метод проверки параметра WorkFlowStatusDtoPreview на null
     *
     * @param status WorkFlowStatusDtoPreview
     * @return WorkFlowStatus или null
     */
    private WorkFlowStatus checkWorkFlowStatusDto(WorkFlowStatusDtoPreview status) {
        return status != null ? workFlowStatusDtoConverter.toModel(status) : null;
    }

    /**
     * Метод проверки параметра UserDtoPreview на null
     *
     * @param executor UserDtoPreview
     * @return User или null
     */
    private User checkUserDto(UserDtoPreview executor) {
        return executor != null ? userDtoConverter.toModel(executor) : null;
    }

    /**
     * Метод проверки параметра TaskPriorityDtoPreview на null
     *
     * @param priority TaskPriorityDtoPreview
     * @return TaskPriority или null
     */
    private TaskPriority checkTaskPriorityDto(TaskPriorityDtoPreview priority) {
        return priority != null ? taskPriorityDtoConverter.toModel(priority) : null;
    }

    /**
     * Метод конвертирует лист из Dto в VO
     *
     * @param attachments лист Dto файлов задачи
     * @return лист VO файлов задачи
     */
    private List<TaskAttachment> listDtoToVoTaskAttachment(List<TaskAttachmentDtoPreview> attachments) {
        if (attachments == null || attachments.isEmpty())
            return Collections.emptyList();
        else
            return attachments.stream()
                    .map(taskAttachmentDtoConverter::toModel)
                    .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из Dto в VO
     *
     * @param relatedTasks лист Dto связанных задач
     * @return лист VO связанных задач
     */
    private List<RelatedTask> listDtoToVoRelatedTask(List<RelatedTaskDtoPreview> relatedTasks) {
        if (relatedTasks == null || relatedTasks.isEmpty())
            return Collections.emptyList();
        else
            return relatedTasks.stream()
                    .map(relatedTaskDtoConverter::toModel)
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
                    checkTaskPriorityModel(model.getPriority()),
                    projectDtoConverter.toDto(model.getProject()),
                    userDtoConverter.toDto(model.getAuthor()),
                    checkUserModel(model.getExecutor()),
                    model.getCreated(),
                    model.getUpdated(),
                    checkWorkFlowStatusModel(model.getStatus()),
                    listVoToDtoWorkFlowAction(model.getStatus()),
                    model.getEstimation(),
                    model.getTimeSpent(),
                    model.getTimeLeft(),
                    listVoToDtoRelatedTask(model.getRelatedTasks()),
                    listVoToDtoTaskAttachment(model.getAttachments())
            );
    }

    /**
     * Метод проверки параметра WorkFlowStatus на null
     *
     * @param status WorkFlowStatus
     * @return WorkFlowStatusDtoPreview или null
     */
    private WorkFlowStatusDtoPreview checkWorkFlowStatusModel(WorkFlowStatus status) {
        return status != null ? workFlowStatusDtoConverter.toDto(status) : null;
    }

    /**
     * Метод проверки параметра User на null
     *
     * @param executor User
     * @return UserDtoPreview или null
     */
    private UserDtoPreview checkUserModel(User executor) {
        return executor != null ? userDtoConverter.toDto(executor) : null;
    }

    /**
     * Метод проверки параметра TaskPriority на null
     *
     * @param priority TaskPriority
     * @return TaskPriorityDtoPreview или null
     */
    private TaskPriorityDtoPreview checkTaskPriorityModel(TaskPriority priority) {
        return priority != null ? taskPriorityDtoConverter.toDto(priority) : null;
    }

    /**
     * Метод конвертирует лист из VO в Dto
     *
     * @param status WorkFlowStatus
     * @return лист Dto WorkFlowAction задачи
     */
    private List<WorkFlowActionDtoPreview> listVoToDtoWorkFlowAction(WorkFlowStatus status) {
        if (status == null || status.getActions().isEmpty())
            return Collections.emptyList();
        else
            return status.getActions().stream()
                    .map(workFlowActionDtoConverter::toDto)
                    .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Dto
     *
     * @param attachments лист VO файлов задачи
     * @return лист Dto файлов задачи
     */
    private List<TaskAttachmentDtoPreview> listVoToDtoTaskAttachment(List<TaskAttachment> attachments) {
        if (attachments == null || attachments.isEmpty())
            return Collections.emptyList();
        else
            return attachments.stream()
                    .map(taskAttachmentDtoConverter::toDto)
                    .collect(Collectors.toList());
    }

    /**
     * Метод конвертирует лист из VO в Dto
     *
     * @param relatedTasks лист VO связанных задач
     * @return лист Dto связанных задач
     */
    private List<RelatedTaskDtoPreview> listVoToDtoRelatedTask(List<RelatedTask> relatedTasks) {
        if (relatedTasks == null || relatedTasks.isEmpty())
            return Collections.emptyList();
        else
            return relatedTasks.stream()
                    .map(relatedTaskDtoConverter::toDto)
                    .collect(Collectors.toList());
    }
}
