package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserRoleDto;
import ru.progwards.tasktracker.service.api.impl.AccessRuleService;
import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.service.vo.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoleDtoConverter implements Converter<UserRole, UserRoleDto> {
    @Autowired
    private AccessRuleDtoConverter accessRuleConverter;
    @Autowired
    private AccessRuleService accessRuleService;
    @Override
    public UserRole toModel(UserRoleDto dto) {
        if (dto == null)
            return null;
        HashMap<Long, AccessRule> ruleMap = new HashMap<>();
        List<Long> ruleIds = dto.getAccessRules();
        for (Long ruleId : ruleIds) {
            AccessRule rule = accessRuleService.get(ruleId);
            ruleMap.put(ruleId, rule);
        }
        return new UserRole(dto.getId(), dto.getName(), dto.getSystemRole(), ruleMap);
    }

    @Override
    public UserRoleDto toDto(UserRole model) {
        if (model == null)
            return null;
        return new UserRoleDto(model.getId(), model.getName(), model.getSystemRole(),
                model.getAccessRules().values().stream().map(AccessRule::getId).collect(Collectors.toList()));
    }
}
