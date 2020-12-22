package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.dto.WorkFlowDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.model.WorkFlow;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class TaskTypeDtoFullConverter implements Converter<TaskType, TaskTypeDtoFull> {

    @Autowired
    private Converter<WorkFlow, WorkFlowDtoPreview> workFlowDtoConverter;
    @Autowired
    private GetService<Long, TaskType> taskTypeGetService;

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
        else {
            TaskType taskType = taskTypeGetService.get(dto.getId());
            return new TaskType(
                    dto.getId(),
                    taskType.getProject(),
                    workFlowDtoConverter.toModel(dto.getWorkFlow()),
                    dto.getName(),
                    taskType.getTasks()
            );
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
                    workFlowDtoConverter.toDto(model.getWorkFlow()),
                    model.getName()
            );
    }
}
