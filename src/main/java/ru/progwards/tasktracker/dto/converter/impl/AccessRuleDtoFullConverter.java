package ru.progwards.tasktracker.dto.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.AccessRuleDtoFull;
import ru.progwards.tasktracker.dto.UserRoleDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.model.types.AccessObject;
import ru.progwards.tasktracker.model.types.AccessType;
import ru.progwards.tasktracker.service.GetService;

/**
 * @author Artem Dikov, Oleg Kiselev
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
                    stringAccessObjectToEnum(dto.getAccessObject().trim()),
                    dto.getPropertyName().trim(),
                    dto.getObjectId(),
                    stringAccessTypeToEnum(dto.getAccessType())
            );
        }
        AccessRule accessRule = accessRuleGetService.get(dto.getId());
        accessRule.setUserRole(userRole);
        accessRule.setObject(stringAccessObjectToEnum(dto.getAccessObject().trim()));
        accessRule.setPropertyName(dto.getPropertyName().trim());
        accessRule.setObjectId(dto.getObjectId());
        accessRule.setAccessType(stringAccessTypeToEnum(dto.getAccessType()));
        return accessRule;
    }

    private AccessObject stringAccessObjectToEnum(String accessObject) {
        if (accessObject != null)
            for (AccessObject value : AccessObject.values()) {
                if (value.name().equalsIgnoreCase(accessObject))
                    return value;
            }

        throw new BadRequestException(
                accessObject + " does not match any enumeration AccessObject!"
        );
    }

    private AccessType stringAccessTypeToEnum(String accessType) {
        if (accessType != null)
            for (AccessType value : AccessType.values()) {
                if (value.name().equalsIgnoreCase(accessType))
                    return value;
            }

        throw new BadRequestException(
                accessType + " does not match any enumeration AccessType!"
        );
    }

    @Override
    public AccessRuleDtoFull toDto(AccessRule model) {
        if (model == null)
            return null;
        return new AccessRuleDtoFull(
                model.getId(),
                userRoleDtoPreviewConverter.toDto(model.getUserRole()),
                model.getObject().name(),
                model.getPropertyName(),
                model.getObjectId(),
                model.getAccessType().name()
        );
    }
}