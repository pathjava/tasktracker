package ru.progwards.tasktracker.repository.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.AccessRuleEntity;
import ru.progwards.tasktracker.repository.entity.UserRoleEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.service.vo.UserRole;

@Component
public class UserRoleConverter implements Converter<UserRoleEntity, UserRole> {

    @Autowired
    private Converter<AccessRuleEntity, AccessRule> accessRuleConverter;

    @Override
    public UserRole toVo(UserRoleEntity entity) {
//        if (entity == null)
//            return null;
//        HashMap<Long, AccessRule> ruleMap = new HashMap<>();
//        List<AccessRuleEntity> rules = entity.getAccessRules();
//        for (AccessRuleEntity rule : rules) {
//            ruleMap.put(rule.getId(), accessRuleConverter.toVo(rule));
//        }
//        return new UserRole(entity.getId(), entity.getName(), entity.getSystemRole(), ruleMap);
        return null;
    }

    @Override
    public UserRoleEntity toEntity(UserRole valueObject) {
//        if (valueObject == null)
//            return null;
//        return new UserRoleEntity(valueObject.getId(), valueObject.getName(), valueObject.getSystemRole(),
//                valueObject.getAccessRules().values().stream().map(v -> accessRuleConverter.toEntity(v)).collect(Collectors.toList()));
        return null;
    }
}
