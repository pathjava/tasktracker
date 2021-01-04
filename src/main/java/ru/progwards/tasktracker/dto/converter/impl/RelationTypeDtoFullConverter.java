package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.RelationTypeDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.service.GetService;

import java.util.Collections;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class RelationTypeDtoFullConverter implements Converter<RelationType, RelationTypeDtoFull> {

    private final GetService<Long, RelationType> relationTypeGetService;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public RelationType toModel(RelationTypeDtoFull dto) {
        if (dto.getId() == null) {
            return new RelationType(
                    null,
                    dto.getName(),
                    checkCounterRelationDto(dto.getCounterRelationId()),
                    Collections.emptyList()
            );
        } else {
            RelationType relationType = relationTypeGetService.get(dto.getId());
            relationType.setName(dto.getName());
            relationType.setCounterRelation(relationTypeGetService.get(dto.getCounterRelationId()));
            return relationType;
        }
    }

    /**
     * Метод проверки существования встречного типа отношения
     *
     * @param id идентификатор RelationType
     * @return RelationType или null
     */
    private RelationType checkCounterRelationDto(Long id) {
        return id != null ? relationTypeGetService.get(id) : null;
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public RelationTypeDtoFull toDto(RelationType model) {
        return new RelationTypeDtoFull(
                model.getId(),
                model.getName(),
                checkCounterRelationModel(model.getCounterRelation())
        );
    }

    /**
     * Метод проверки существования встречного типа отношения
     *
     * @param relationType RelationType
     * @return id идентификатор RelationType или null
     */
    private Long checkCounterRelationModel(RelationType relationType) {
        return relationType != null ? relationType.getCounterRelation().getCounterRelation().getId() : null;
    }
}
