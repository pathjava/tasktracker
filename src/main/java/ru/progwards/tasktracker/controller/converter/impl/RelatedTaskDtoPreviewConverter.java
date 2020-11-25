package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelatedTaskDtoPreview;
import ru.progwards.tasktracker.controller.dto.RelationTypeDtoPreview;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

/**
 * @author Oleg Kiselev
 */
@Component
public class RelatedTaskDtoPreviewConverter implements Converter<RelatedTask, RelatedTaskDtoPreview> {

    @Autowired
    private Converter<RelationType, RelationTypeDtoPreview> typeDtoPreviewConverter;

    @Override
    public RelatedTask toModel(RelatedTaskDtoPreview dto) {
        if (dto == null)
            return null;
        else
            return new RelatedTask(
                    dto.getId(),
                    typeDtoPreviewConverter.toModel(dto.getRelationType()),
                    dto.getCurrentTaskId(),
                    dto.getAttachedTaskId()
            );
    }

    @Override
    public RelatedTaskDtoPreview toDto(RelatedTask model) {
        if (model == null)
            return null;
        else
            return new RelatedTaskDtoPreview(
                    model.getId(),
                    typeDtoPreviewConverter.toDto(model.getRelationType()),
                    model.getCurrentTaskId(),
                    model.getAttachedTaskId()
            );
    }
}
