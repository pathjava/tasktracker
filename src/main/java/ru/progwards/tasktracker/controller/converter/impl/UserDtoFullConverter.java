package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserDtoFull;
import ru.progwards.tasktracker.controller.dto.UserRoleDtoPreview;
import ru.progwards.tasktracker.service.vo.UserRole;
import ru.progwards.tasktracker.service.vo.User;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Преобразование valueObject <-> dto
 * User <-> UserDtoFull
 * @author Aleksandr Sidelnikov
 */
@Component
public class UserDtoFullConverter implements Converter<User, UserDtoFull> {
    @Autowired
    private Converter<UserRole, UserRoleDtoPreview> userRoleDtoConverter;
    /**
     * Преобразовать в бизнес-объект
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public User toModel(UserDtoFull dto) {
//        return new User(dto.getId(),
//                dto.getName(),
//                dto.getEmail(),
//                dto.getPassword(),
//                listDtoToVoUserRole(dto.getRoles()));
        return null;
    }
    /**
     * Метод конвертирует лист из Dto в VO
     * @param actions лист Dto UserRole
     * @return лист VO UserRole
     */
    private List<UserRole> listDtoToVoUserRole(List<UserRoleDtoPreview> actions) {
        return actions.stream()
                .map(dto -> userRoleDtoConverter.toModel(dto))
                .collect(Collectors.toList());
    }
    /**
     * Преобразовать в сущность dto
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public UserDtoFull toDto(User model) {
//        return new UserDtoFull(model.getId(),
//                model.getName(),
//                model.getEmail(),
//                model.getPassword(),
//                listVoToDtoUserRole(model.getRoles()));
        return null;
    }
    /**
     * Метод конвертирует лист из VO в Dto
     * @param actions лист VO UserRole задачи
     * @return лист Dto UserRole задачи
     */
    private List<UserRoleDtoPreview> listVoToDtoUserRole(List<UserRole> actions) {
        return actions.stream()
                .map(model -> userRoleDtoConverter.toDto(model))
                .collect(Collectors.toList());
    }

}