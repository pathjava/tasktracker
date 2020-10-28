package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.AccessRuleDto;
import ru.progwards.tasktracker.service.vo.AccessRule;

@Component
public class AccessRuleDtoConverter implements Converter<AccessRule, AccessRuleDto> {
    @Override
    public AccessRule toModel(AccessRuleDto dto) {
        if (dto == null)
            return null;
        return new AccessRule(dto.getId(), dto.getObjectName(), dto.getPropertyName(), dto.getObjectId(),
                dto.getAccessType());
    }

    @Override
    public AccessRuleDto toDto(AccessRule model) {
        if (model == null)
            return null;
        return new AccessRuleDto(model.getId(), model.getObjectName(), model.getPropertyName(), model.getObjectId(),
                model.getAccessType());
    }
}
