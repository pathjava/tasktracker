package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.UserDtoFull;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDtoPreviewConverter implements Converter<User, UserDtoPreview> {

    private final @NonNull GetService<Long, User> userGetService;

    @Override
    public User toModel(UserDtoPreview dto) {
        return userGetService.get(dto.getId());
    }

    @Override
    public UserDtoPreview toDto(User model) {
        return new UserDtoPreview(model.getId(),
                model.getName());
    }
}
