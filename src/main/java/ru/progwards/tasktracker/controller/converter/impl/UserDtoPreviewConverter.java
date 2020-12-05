package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserDtoFull;
import ru.progwards.tasktracker.controller.dto.UserDtoPreview;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;

/**
 * Преобразование valueObject <-> dto
 * User <-> UserDtoFull
 * @author Aleksandr Sidelnikov
 */
@Component
public class UserDtoPreviewConverter implements Converter<User, UserDtoPreview> {
    @Autowired
    private GetService<Long, User> userGetService;

    @Override
    public User toModel(UserDtoPreview dto) {
        User user = userGetService.get(dto.getId());
        return new User(dto.getId(),
                dto.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles());
    }

    @Override
    public UserDtoPreview toDto(User model) {
        return new UserDtoPreview(model.getId(),
                model.getName());
    }
}
