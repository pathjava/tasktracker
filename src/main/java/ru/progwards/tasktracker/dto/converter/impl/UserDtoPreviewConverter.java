package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.User;

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
//        User user = userGetService.get(dto.getId());
//        return new User(dto.getId(),
//                dto.getName(),
//                user.getEmail(),
//                user.getPassword(),
//                user.getRoles());
        return null;
    }

    @Override
    public UserDtoPreview toDto(User model) {
        return new UserDtoPreview(model.getId(),
                model.getName());
    }
}
