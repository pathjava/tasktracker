package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserDtoPreview;
import ru.progwards.tasktracker.service.vo.User;

@Component
public class UserDtoPreviewConverter implements Converter<User, UserDtoPreview> {
    @Override
    public User toModel(UserDtoPreview dto) {
        return null;
    }

    @Override
    public UserDtoPreview toDto(User model) {
        return null;
    }
}
