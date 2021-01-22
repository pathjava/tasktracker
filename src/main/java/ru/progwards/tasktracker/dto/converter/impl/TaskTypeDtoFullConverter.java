package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.dto.WorkFlowDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.service.GetService;

import java.util.Collections;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskTypeDtoFullConverter implements Converter<TaskType, TaskTypeDtoFull> {

    private final Converter<WorkFlow, WorkFlowDtoPreview> workFlowDtoConverter;
    private final Converter<Project, ProjectDtoPreview> projectDtoConverter;
    private final GetService<Long, TaskType> taskTypeGetService;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public TaskType toModel(TaskTypeDtoFull dto) {
        if (dto == null)
            return null;
        else if (dto.getId() == null) {
            return new TaskType(
                    null,
                    checkProjectDto(dto.getProject()),
                    checkWorkFlowDto(dto.getWorkFlow()),
                    dto.getName().toLowerCase().trim(),
                    Collections.emptyList()
            );
        } else {
            TaskType taskType = taskTypeGetService.get(dto.getId());
            taskType.setProject(checkProjectDto(dto.getProject()));
            taskType.setWorkFlow(checkWorkFlowDto(dto.getWorkFlow()));
            taskType.setName(dto.getName().trim());
            return taskType;
        }
    }

    /**
     * Метод проверки параметра WorkFlowDtoPreview на null
     *
     * @param workFlow WorkFlowDtoPreview
     * @return WorkFlow или null
     */
    private WorkFlow checkWorkFlowDto(WorkFlowDtoPreview workFlow) {
        return workFlow != null ? workFlowDtoConverter.toModel(workFlow) : null;
    }

    /**
     * Метод проверки параметра ProjectDtoPreview на null
     *
     * @param project ProjectDtoPreview
     * @return Project или null
     */
    private Project checkProjectDto(ProjectDtoPreview project) {
        return project != null ? projectDtoConverter.toModel(project) : null;
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public TaskTypeDtoFull toDto(TaskType model) {
        if (model == null)
            return null;
        else
            return new TaskTypeDtoFull(
                    model.getId(),
                    checkProjectModel(model.getProject()),
                    checkWorkFlowModel(model.getWorkFlow()),
                    model.getName()
            );
    }

    /**
     * Метод проверки параметра WorkFlow на null
     *
     * @param workFlow WorkFlow
     * @return WorkFlowDtoPreview или null
     */
    private WorkFlowDtoPreview checkWorkFlowModel(WorkFlow workFlow) {
        return workFlow != null ? workFlowDtoConverter.toDto(workFlow) : null;
    }

    /**
     * Метод проверки параметра Project на null
     *
     * @param project Project
     * @return ProjectDtoPreview или null
     */
    private ProjectDtoPreview checkProjectModel(Project project) {
        return project != null ? projectDtoConverter.toDto(project) : null;
    }
}
