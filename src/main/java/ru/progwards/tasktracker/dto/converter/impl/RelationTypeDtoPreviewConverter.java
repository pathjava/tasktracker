package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.RelationTypeDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.RelationType;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class RelationTypeDtoPreviewConverter implements Converter<RelationType, RelationTypeDtoPreview> {

    @Autowired
    private GetService<Long, RelationType> getService;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public RelationType toModel(RelationTypeDtoPreview dto) {
        if (dto == null)
            return null;
        else {
            RelationType relationType = getService.get(dto.getId());
            return new RelationType(
                    dto.getId(),
                    dto.getName(),
                    relationType.getCounterRelation(),
                    relationType.getRelatedTasks()
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
