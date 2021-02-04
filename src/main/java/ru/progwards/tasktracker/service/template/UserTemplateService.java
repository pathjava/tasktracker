package ru.progwards.tasktracker.service.template;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.TemplateService;

import java.util.ArrayList;
import java.util.List;

/**
 * Шаблон объекта User
 *
 * @author Konstantin Kishkin
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserTemplateService implements TemplateService<User> {

    CreateService<User> userCreateService;

    /**
     * Создание по шаблону
     * @param args [0] UserRole
     * @return list of User's
     */
    @Override
    public List<User> createFromTemplate(Object... args) {

        if (args.length != 1)
            throw new OperationIsNotPossibleException("User.createFromTemplate: 1 argument expected");
        if (!(args[0] instanceof UserRole))
            throw new OperationIsNotPossibleException("User.createFromTemplate: argument 0 must be UserRole");

        List <UserRole> userRoleList = new ArrayList<>();
        userRoleList.add((UserRole) args[0]);
        UserRole userRole = (UserRole) args[0];

        User user = new User();
        user.setName("admin");
        user.setEmail("userMail@mail.com");
        user.setPassword("12345");
        user.setRoles(userRoleList);
        userCreateService.create(user);

        return List.of(user);
    }
}

