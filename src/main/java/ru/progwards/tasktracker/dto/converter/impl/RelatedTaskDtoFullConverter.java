package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.RelatedTaskDtoFull;
import ru.progwards.tasktracker.dto.RelationTypeDtoPreview;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.service.GetService;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class RelatedTaskDtoFullConverter implements Converter<RelatedTask, RelatedTaskDtoFull> {

    private final Converter<RelationType, RelationTypeDtoPreview> relationTypeDtoConverter;
    private final Converter<Task, TaskDtoPreview> taskDtoConverter;
    private final GetService<Long, RelatedTask> relatedTaskGetService;
    private final GetService<Long, Task> taskGetService;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public RelatedTask toModel(RelatedTaskDtoFull dto) {
        if (dto == null)
            return null;
        else if (dto.getId() == null) {
            return new RelatedTask(
                    null,
                    relationTypeDtoConverter.toModel(dto.getRelationType()),
                    taskGetService.get(dto.getCurrentTaskId()),
                    taskDtoConverter.toModel(dto.getAttachedTask()),
                    false
            );
        } else {
            RelatedTask relatedTask = relatedTaskGetService.get(dto.getId());
            relatedTask.setRelationType(relationTypeDtoConverter.toModel(dto.getRelationType()));
            relatedTask.setCurrentTask(taskGetService.get(dto.getCurrentTaskId()));
            relatedTask.setAttachedTask(taskDtoConverter.toModel(dto.getAttachedTask()));
            return relatedTask;
        }
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public RelatedTaskDtoFull toDto(RelatedTask model) {
        if (model == null)
            return null;
        else
            return new RelatedTaskDtoFull(
                    model.getId(),
                    relationTypeDtoConverter.toDto(model.getRelationType()),
                    model.getCurrentTask().getId(),
                    taskDtoConverter.toDto(model.getAttachedTask())
            );
    }
}
