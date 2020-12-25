package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.RelatedTaskDtoPreview;
import ru.progwards.tasktracker.dto.RelationTypeDtoPreview;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RelatedTaskDtoPreviewConverter implements Converter<RelatedTask, RelatedTaskDtoPreview> {

    private final @NonNull Converter<RelationType, RelationTypeDtoPreview> typeDtoConverter;
    private final @NonNull Converter<Task, TaskDtoPreview> taskDtoConverter;
    private final @NonNull GetService<Long, Task> taskGetService;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public RelatedTask toModel(RelatedTaskDtoPreview dto) {
        if (dto == null)
            return null;
        else {
            Task currentTask = taskGetService.get(dto.getCurrentTaskId());
            return new RelatedTask(
                    dto.getId(),
                    typeDtoConverter.toModel(dto.getRelationType()),
                    currentTask,
                    taskDtoConverter.toModel(dto.getAttachedTask()),
                    false //TODO - check!!!
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
    public RelatedTaskDtoPreview toDto(RelatedTask model) {
        if (model == null)
            return null;
        else
            return new RelatedTaskDtoPreview(
                    model.getId(),
                    typeDtoConverter.toDto(model.getRelationType()),
                    model.getCurrentTask().getId(),
                    taskDtoConverter.toDto(model.getAttachedTask())
            );
    }
}
