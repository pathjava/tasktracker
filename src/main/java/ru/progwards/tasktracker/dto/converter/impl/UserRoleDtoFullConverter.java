package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.UserRoleDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.model.types.EstimateChange;
import ru.progwards.tasktracker.model.types.SystemRole;
import ru.progwards.tasktracker.service.GetService;

import java.util.Collections;

/**
 * @author Artem Dikov, Oleg Kiselev
 */

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class UserRoleDtoFullConverter implements Converter<UserRole, UserRoleDtoFull> {

    private final GetService<Long, UserRole> userRoleGetService;

    @Override
    public UserRole toModel(UserRoleDtoFull dto) {
        if (dto == null)
            return null;
        if (dto.getId() == null)
            return new UserRole(
                    null,
                    dto.getName(),
                    stringToEnum(dto.getSystemRole()),
                    Collections.emptyList(),
                    Collections.emptyList()
            );
        UserRole userRole = userRoleGetService.get(dto.getId());
        userRole.setName(dto.getName());
        userRole.setSystemRole(stringToEnum(dto.getSystemRole()));
        //userRole.setAccessRules(dto.getAccessRules().stream().map(accessRuleDtoFullConverter::toModel).collect(Collectors.toList()));
        return userRole;
    }

    private SystemRole stringToEnum(String systemRole) {
        if (systemRole != null)
            for (SystemRole value : SystemRole.values()) {
                if (value.name().equalsIgnoreCase(systemRole))
                    return value;
            }

        throw new BadRequestException(
                systemRole + " does not match any enumeration SystemRole!"
        );
    }

    @Override
    public UserRoleDtoFull toDto(UserRole model) {
        if (model == null)
            return null;
        return new UserRoleDtoFull(
                model.getId(),
                model.getName(),
                model.getSystemRole().name()
        );
    }
}