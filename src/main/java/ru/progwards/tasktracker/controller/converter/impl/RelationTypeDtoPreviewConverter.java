package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelationTypeDtoPreview;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.RelationType;

/**
 * @author Oleg Kiselev
 */
@Component
public class RelationTypeDtoPreviewConverter implements Converter<RelationType, RelationTypeDtoPreview> {

    @Autowired
    private GetService<Long, RelationType> getService;

    @Override
    public RelationType toModel(RelationTypeDtoPreview dto) {
        if (dto == null)
            return null;
        else {
            RelationType relationType = getService.get(dto.getId());
            return new RelationType(
                    dto.getId(),
                    dto.getName(),
                    relationType.getCounterRelationId()
            );
        }
    }

    @Override
    public RelationTypeDtoPreview toDto(RelationType model) {
        if (model == null)
            return null;
        else
            return new RelationTypeDtoPreview(
                    model.getId(),
                    model.getName()
            );
    }
}
