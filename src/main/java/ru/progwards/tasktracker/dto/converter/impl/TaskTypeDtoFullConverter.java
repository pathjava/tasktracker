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
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
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
                    projectDtoConverter.toModel(dto.getProject()),
                    workFlowDtoConverter.toModel(dto.getWorkFlow()),
                    dto.getName(),
                    Collections.emptyList()
            );
        } else {
            TaskType taskType = taskTypeGetService.get(dto.getId());
            taskType.setProject(projectDtoConverter.toModel(dto.getProject()));
            taskType.setWorkFlow(workFlowDtoConverter.toModel(dto.getWorkFlow()));
            taskType.setName(dto.getName());
            return taskType;
        }
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
                    projectDtoConverter.toDto(model.getProject()),
                    workFlowDtoConverter.toDto(model.getWorkFlow()),
                    model.getName()
            );
    }
}
