package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserRoleDtoPreview;
import ru.progwards.tasktracker.service.vo.UserRole;

@Component
public class UserRoleDtoPreviewConverter implements Converter<UserRole, UserRoleDtoPreview> {
    @Override
    public UserRole toModel(UserRoleDtoPreview dto) {
        return null;
    }

    @Override
    public UserRoleDtoPreview toDto(UserRole model) {
        return null;
    }
}
