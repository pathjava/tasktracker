package ru.progwards.tasktracker.dto.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.UserRoleDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.service.GetService;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Artem Dikov
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleDtoFullConverter implements Converter<UserRole, UserRoleDtoFull> {

    private final AccessRuleDtoFullConverter accessRuleDtoFullConverter;
    private final GetService<Long, UserRole> userRoleGetService;

    @Override
    public UserRole toModel(UserRoleDtoFull dto) {
        if (dto == null)
            return null;
        if (dto.getId() == null)
            return new UserRole(
                    null,
                    dto.getName(),
                    dto.getSystemRole(),
                    dto.getAccessRules().stream().map(accessRuleDtoFullConverter::toModel).collect(Collectors.toList()),
                    Collections.emptyList());
        UserRole userRole = userRoleGetService.get(dto.getId());
        userRole.setName(dto.getName());
        userRole.setSystemRole(dto.getSystemRole());
        userRole.setAccessRules(dto.getAccessRules().stream().map(accessRuleDtoFullConverter::toModel).collect(Collectors.toList()));
        return userRole;
    }

    @Override
    public UserRoleDtoFull toDto(UserRole model) {
        if (model == null)
            return null;
        return new UserRoleDtoFull(
                model.getId(),
                model.getName(),
                model.getSystemRole(),
                model.getAccessRules().stream().map(accessRuleDtoFullConverter::toDto).collect(Collectors.toList()));
    }
}
