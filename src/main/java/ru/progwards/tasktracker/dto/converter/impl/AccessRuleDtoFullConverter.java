package ru.progwards.tasktracker.dto.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.AccessRuleDtoFull;
import ru.progwards.tasktracker.dto.UserRoleDtoPreview;
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

    private final Converter<UserRole, UserRoleDtoPreview> userRoleDtoPreviewConverter;
    private final GetService<Long, AccessRule> accessRuleGetService;

    @Override
    public AccessRule toModel(AccessRuleDtoFull dto) {
        if (dto == null)
            return null;
        UserRole userRole = dto.getUserRole() == null ? null : userRoleDtoPreviewConverter.toModel(dto.getUserRole());
        if (dto.getId() == null) {
            return new AccessRule(
                    null,
                    userRole,
                    dto.getObject(),
                    dto.getPropertyName(),
                    dto.getObjectId(),
                    dto.getAccessType()
            );
        }
        AccessRule accessRule = accessRuleGetService.get(dto.getId());
        accessRule.setUserRole(userRole);
        accessRule.setObject(dto.getObject());
        accessRule.setPropertyName(dto.getPropertyName());
        accessRule.setObjectId(dto.getObjectId());
        accessRule.setAccessType(dto.getAccessType());
        return accessRule;
    }

    @Override
    public AccessRuleDtoFull toDto(AccessRule model) {
        if (model == null)
            return null;
        return new AccessRuleDtoFull(
                model.getId(),
                userRoleDtoPreviewConverter.toDto(model.getUserRole()),
                model.getObject(),
                model.getPropertyName(),
                model.getObjectId(),
                model.getAccessType()
        );
    }
}