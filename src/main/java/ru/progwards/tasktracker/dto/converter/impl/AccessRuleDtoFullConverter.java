package ru.progwards.tasktracker.dto.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.AccessRuleDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.service.GetService;

/**
 * @author Artem Dikov
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessRuleDtoFullConverter implements Converter<AccessRule, AccessRuleDtoFull> {

    private final GetService<Long, UserRole> userRoleGetService;
    private final GetService<Long, AccessRule> accessRuleGetService;

    @Override
    public AccessRule toModel(AccessRuleDtoFull dto) {
        if (dto == null)
            return null;
        UserRole userRole = dto.getUserRoleId() == null ? null : userRoleGetService.get(dto.getUserRoleId());
        if (dto.getId() == null) {
            return new AccessRule(
                    null,
                    dto.getObjectName(),
                    dto.getPropertyName(),
                    dto.getObjectId(),
                    dto.getAccessType(),
                    userRole
            );
        }
        AccessRule accessRule = accessRuleGetService.get(dto.getId());
        accessRule.setObjectName(dto.getObjectName());
        accessRule.setPropertyName(dto.getPropertyName());
        accessRule.setObjectId(dto.getObjectId());
        accessRule.setAccessType(dto.getAccessType());
        accessRule.setUserRole(userRole);
        return accessRule;
    }

    @Override
    public AccessRuleDtoFull toDto(AccessRule model) {
        if (model == null)
            return null;
        return new AccessRuleDtoFull(model.getId(), model.getObjectName(), model.getPropertyName(), model.getObjectId(),
                model.getAccessType(), model.getUserRole().getId());
    }
}
