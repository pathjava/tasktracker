package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelatedTaskDto;
import ru.progwards.tasktracker.service.vo.RelatedTask;

/**
 * конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskDtoConverter implements Converter<RelatedTask, RelatedTaskDto> {
    /**
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public RelatedTask toModel(RelatedTaskDto dto) {
        if (dto == null)
            return null;
        else
            return new RelatedTask(
                    checkIdNotNull(dto),
                    dto.getRelationType(),
                    dto.getParentTaskId(),
                    dto.getTaskId()
            );
    }

    private Long checkIdNotNull(RelatedTaskDto dto) {
        return dto.getId() == null ? null : dto.getId();
    }

    /**
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public RelatedTaskDto toDto(RelatedTask model) {
        if (model == null)
            return null;
        else
            return new RelatedTaskDto(
                    model.getId(),
                    model.getRelationType(),
                    model.getParentTaskId(),
                    model.getTaskId()
            );
    }
}
