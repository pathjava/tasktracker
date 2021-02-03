package ru.progwards.tasktracker.service.template;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.model.types.SystemRole;
import ru.progwards.tasktracker.repository.UserRoleRepository;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Dikov
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleTemplateService implements TemplateService<UserRole> {

    private final CreateService<UserRole> userRoleCreateService;
    private final TemplateService<AccessRule> accessRuleTemplateService;

//    /**
//     * Метод создания UserRole по шаблону
//     *
//     * @param args – [0] - SystemRole, [1] - User(менеджер проектов)
//     */

    @Override
    public List<UserRole> createFromTemplate(Object... args) {

        List<UserRole> result = new ArrayList<>();
//        if (args.length != 2)
//            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: arguments expected");
//        if (!(args[0] instanceof SystemRole))
//            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: argument 0 must be SystemRole");
//        if (!(args[1] instanceof User))
//            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: argument 1 must be User");
//        if (!(args[2] instanceof User))
//            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: argument 2 must be User");

        UserRole userRole1 = new UserRole();
        userRole1.setSystemRole(SystemRole.ADMIN);
        userRole1.setName("Администраторы");
        userRole1.setUsers(List.of((User)args[1]));
        userRoleCreateService.create(userRole1);

        UserRole userRole2 = new UserRole();
        userRole2.setSystemRole(SystemRole.USER);
        userRole2.setName("Пользователи");
        userRole2.setAccessRules(accessRuleTemplateService.createFromTemplate());
        userRoleCreateService.create(userRole2);
        result.add(userRole1);
        result.add(userRole2);
        return result;
    }
}