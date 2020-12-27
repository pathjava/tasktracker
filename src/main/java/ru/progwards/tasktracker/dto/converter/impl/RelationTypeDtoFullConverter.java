package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.RelationTypeDtoFull;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.RelationType;

import java.util.Collections;
import java.util.List;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RelationTypeDtoFullConverter implements Converter<RelationType, RelationTypeDtoFull> {

    private final @NonNull GetService<Long, RelationType> getService;

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
        else if(dto.getId() == null) {
            return new RelationType(
                    null,
                    dto.getName(),
                    checkCounterRelation(dto.getCounterRelation()),
                    Collections.emptyList()
            );
        } else {
            RelationType relationType = getService.get(dto.getId());
            relationType.setName(dto.getName());
            relationType.setCounterRelation(toModel(dto.getCounterRelation()));
            return relationType;
        }
    }

    private RelationType checkCounterRelation(RelationTypeDtoFull counterRelation) {
        return counterRelation != null ? toModel(counterRelation) : null;
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
