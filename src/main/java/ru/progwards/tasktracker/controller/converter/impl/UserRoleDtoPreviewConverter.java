package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserRoleDtoPreview;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.service.vo.UserRole;

@Component
public class UserRoleDtoPreviewConverter implements Converter<UserRole, UserRoleDtoPreview> {

    @Autowired
    private GetService<Long, AccessRule> accessRuleGetService;

    @Autowired
    private GetService<Long, UserRole> userRoleGetService;

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
        return new UserRoleDtoPreview(model.getId(), model.getName());
    }
}
