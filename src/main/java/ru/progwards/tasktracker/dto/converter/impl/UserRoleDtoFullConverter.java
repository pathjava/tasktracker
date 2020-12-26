package ru.progwards.tasktracker.dto.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.UserRoleDtoFull;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Artem Dikov
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleDtoFullConverter implements Converter<UserRole, UserRoleDtoFull> {

    private final GetService<Long, AccessRule> accessRuleGetService;
    private final UserDtoPreviewConverter userDtoPreviewConverter;

    @Override
    public UserRole toModel(UserRoleDtoFull dto) {
        if (dto == null)
            return null;
        return new UserRole(dto.getId(), dto.getName(), dto.getSystemRole(),
                dto.getAccessRules().stream().map(accessRuleGetService::get).collect(Collectors.toList()),
                dto.getUserDtoPreviewList().stream().map(userDtoPreviewConverter::toModel).collect(Collectors.toList()));
    }

    @Override
    public UserRoleDtoFull toDto(UserRole model) {
        if (model == null)
            return null;
        return new UserRoleDtoFull(model.getId(), model.getName(), model.getSystemRole(),
                model.getAccessRules().stream().map(AccessRule::getId).collect(Collectors.toList()),
                model.getUsers().stream().map(userDtoPreviewConverter::toDto).collect(Collectors.toList()));
    }
}
