package ru.progwards.tasktracker.dto.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.UserRoleDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.UserRole;

/**
 * @author Artem Dikov
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleDtoPreviewConverter implements Converter<UserRole, UserRoleDtoPreview> {

    private final GetService<Long, UserRole> userRoleGetService;

    @Override
    public UserRole toModel(UserRoleDtoPreview dto) {
        if (dto == null)
            return null;
        return userRoleGetService.get(dto.getId());
    }

    @Override
    public UserRoleDtoPreview toDto(UserRole model) {
        if (model == null)
            return null;
        return new UserRoleDtoPreview(model.getId(), model.getName(), model.getSystemRole());
    }
}
