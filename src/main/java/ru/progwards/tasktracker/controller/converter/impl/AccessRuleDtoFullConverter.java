package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.AccessRuleDtoFull;
import ru.progwards.tasktracker.service.vo.AccessRule;

@Component
public class AccessRuleDtoFullConverter implements Converter<AccessRule, AccessRuleDtoFull> {
    @Override
    public AccessRule toModel(AccessRuleDtoFull dto) {
//        if (dto == null)
//            return null;
//        return new AccessRule(dto.getId(), dto.getObjectName(), dto.getPropertyName(), dto.getObjectId(),
//                dto.getAccessType());
        return null;
    }

    @Override
    public AccessRuleDtoFull toDto(AccessRule model) {
        if (model == null)
            return null;
        return new AccessRuleDtoFull(model.getId(), model.getObjectName(), model.getPropertyName(), model.getObjectId(),
                model.getAccessType());
    }
}
