package ru.progwards.tasktracker.service.template;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.service.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Шаблон объекта Project
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectTemplateService implements TemplateService<Project> {

    CreateService<Project> projectCreateService;

    /**
     * Создание по шаблону
     * @param args [0] User
     * @return list of Project's
     */
    @Override
    public List<Project> createFromTemplate(Object... args) {
        if (args.length != 1)
            throw new OperationIsNotPossibleException("Project.createFromTemplate: 1 argument expected");
        if (!(args[0] instanceof User))
            throw new OperationIsNotPossibleException("Project.createFromTemplate: argument 0 must be User");

        User user = (User) args[0];

        Project project = new Project();
        project.setName("Тест");
        project.setDescription("Ознакомительный проект");
        project.setPrefix("TEST");
        project.setOwner(user);
        projectCreateService.create(project);

        return List.of(project);
    }
}
