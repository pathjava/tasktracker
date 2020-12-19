package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.AccessRuleEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.AccessRule;

@Component
public class AccessRuleConverter implements Converter<AccessRuleEntity, AccessRule> {
    @Override
    public AccessRule toVo(AccessRuleEntity entity) {
//        if (entity == null)
//            return null;
//        return new AccessRule(entity.getId(), entity.getObjectName(), entity.getPropertyName(), entity.getObjectId(),
//                entity.getAccessType());
        return null;
    }

    @Override
    public AccessRuleEntity toEntity(AccessRule valueObject) {
        if (valueObject == null)
            return null;
        return new AccessRuleEntity(valueObject.getId(), valueObject.getObjectName(), valueObject.getPropertyName(), valueObject.getObjectId(),
                valueObject.getAccessType());
    }
    //
}
