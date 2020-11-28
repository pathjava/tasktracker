package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelatedTaskDtoFull;
import ru.progwards.tasktracker.controller.dto.RelationTypeDtoFull;
import ru.progwards.tasktracker.controller.dto.TaskDtoPreview;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskDtoFullConverter implements Converter<RelatedTask, RelatedTaskDtoFull> {

    @Autowired
    private Converter<RelationType, RelationTypeDtoFull> typeDtoConverter;
    @Autowired
    private Converter<Task, TaskDtoPreview> taskDtoConverter;

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
        else
            return new RelatedTask(
                    dto.getId(),
                    typeDtoConverter.toModel(dto.getRelationType()),
                    dto.getCurrentTaskId(),
                    taskDtoConverter.toModel(dto.getAttachedTask())
            );
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
                    typeDtoConverter.toDto(model.getRelationType()),
                    model.getCurrentTaskId(),
                    taskDtoConverter.toDto(model.getAttachedTask())
            );
    }
}
