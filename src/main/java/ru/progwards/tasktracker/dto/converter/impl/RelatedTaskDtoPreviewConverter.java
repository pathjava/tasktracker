package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.RelatedTaskDtoPreview;
import ru.progwards.tasktracker.dto.RelationTypeDtoPreview;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.model.Task;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskDtoPreviewConverter implements Converter<RelatedTask, RelatedTaskDtoPreview> {

    @Autowired
    private Converter<RelationType, RelationTypeDtoPreview> typeDtoConverter;
    @Autowired
    private Converter<Task, TaskDtoPreview> taskDtoConverter;
    @Autowired
    private GetService<Long, Task> getService;

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
            Task currentTask = getService.get(dto.getCurrentTaskId());
            return new RelatedTask(
                    dto.getId(),
                    typeDtoConverter.toModel(dto.getRelationType()),
                    currentTask,
                    taskDtoConverter.toModel(dto.getAttachedTask())
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
