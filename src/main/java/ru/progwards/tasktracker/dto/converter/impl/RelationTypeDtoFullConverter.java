package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.RelationTypeDtoFull;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.RelationType;

import java.util.List;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class RelationTypeDtoFullConverter implements Converter<RelationType, RelationTypeDtoFull> {

    @Autowired
    private GetService<Long, RelationType> getService;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public RelationType toModel(RelationTypeDtoFull dto) {
        if (dto == null)
            return null;
        else {
            List<RelatedTask> relatedTasks = getService.get(dto.getId()).getRelatedTasks();
            return new RelationType(
                    dto.getId(),
                    dto.getName(),
                    toModel(dto.getCounterRelation()),
                    relatedTasks
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
    public RelationTypeDtoFull toDto(RelationType model) {
        if (model == null)
            return null;
        else
            return new RelationTypeDtoFull(
                    model.getId(),
                    model.getName(),
                    toDto(model.getCounterRelation())
            );
    }
}
