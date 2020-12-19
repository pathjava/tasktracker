package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserRoleDtoFull;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.service.vo.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoleDtoFullConverter implements Converter<UserRole, UserRoleDtoFull> {

    @Autowired
    private GetService<Long, AccessRule> accessRuleGetService;

    @Override
    public UserRole toModel(UserRoleDtoFull dto) {
//        if (dto == null)
            return null;
//        List<AccessRule> rules = new ArrayList<>();
//        List<Long> ruleIds = dto.getAccessRules();
//        for (Long ruleId : ruleIds) {
//            AccessRule rule = accessRuleGetService.get(ruleId);
//            rules.add(rule);
//        }
//        return new UserRole(dto.getId(), dto.getName(), dto.getSystemRole(), rules);
    }

    @Override
    public UserRoleDtoFull toDto(UserRole model) {
        if (model == null)
            return null;
        return new UserRoleDtoFull(model.getId(), model.getName(), model.getSystemRole(),
//                model.getAccessRules().values().stream().map(AccessRule::getId).collect(Collectors.toList()));
                model.getAccessRules().stream().map(AccessRule::getId).collect(Collectors.toList()));
    }
}
