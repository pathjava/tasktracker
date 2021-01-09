package ru.progwards.tasktracker.repository.deprecated.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.repository.deprecated.entity.AccessRuleEntity;

@Component
@Deprecated
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
//        if (valueObject == null)
//            return null;
//        return new AccessRuleEntity(valueObject.getId(), valueObject.getObjectName(), valueObject.getPropertyName(), valueObject.getObjectId(),
//                valueObject.getAccessType());
        return null;
    }
    //
}
