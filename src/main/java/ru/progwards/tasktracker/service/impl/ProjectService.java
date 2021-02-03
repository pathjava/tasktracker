package ru.progwards.tasktracker.service.impl;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Сервисные методы для объекта Project
 * @author Pavel Khovaylo
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectService implements GetListService<Project>,
                                        GetService<Long, Project>,
                                        CreateService<Project>,
                                        RefreshService<Project>,
                                        RemoveService<Project> {

    /**
     * репозиторий с проектами
     */
    ProjectRepository repository;

    TemplateService<TaskType> taskTypeTemplateService;
    TemplateService<WorkFlow> workflowTemplateService;

    /**
     * метод по получению списка всех проектов, у которых значение свойства deleted = false
     * @return список проектов
     */
    @Override
    public List<Project> getList() {
        return repository.getAllByDeletedIsFalse();
    }
    /**
     * метод по получению проекта
     * @param id идентификатор проекта, который необходимо получить
     * @return бизнес-модель проекта
     */
    @Override
    public Project get(Long id) {
        return repository.findByIdAndDeletedIsFalse(id).
                orElseThrow(() ->
                        new OperationIsNotPossibleException("Project.id = " + id + " doesn't exist"));
    }
    /**
     * метот добавляет проект в репозиторий
     * @param model бизнес-модель
     */
    @Transactional
    @Override
    public void create(Project model) {
        //провряем, нет ли уже проекта с таким кодом
        String prefix = model.getPrefix().toUpperCase();
        if (repository.findByPrefix(prefix).isPresent())
            throw new OperationIsNotPossibleException("Prefix is already exists");

        model.setCreated(ZonedDateTime.now());
        model.setPrefix(prefix);
        model.setLastTaskCode(0L);
        repository.save(model);

        //создаем простой шаблон и стандартные типы задач
        List<WorkFlow> workFlows = workflowTemplateService.createFromTemplate();
        taskTypeTemplateService.createFromTemplate(model, workFlows.get(0));
    }
    /**
     * метод по обновлению проекта
     * @param model бизнес-модель, которую хотим обновить
     */
    @Transactional
    @Override
    public void refresh(Project model) {
        Project project = repository.findByIdAndDeletedIsFalse(model.getId()).orElseThrow(() ->
                new OperationIsNotPossibleException("Project.id = " + model.getId() + " doesn't exist"));

        //если в обновляемом проекте меняем префикс и у проекта имеются задачи, то обновление невозможно
        if (!model.getPrefix().equals(project.getPrefix()) &&
                model.getTasks().size() > 0)
            throw new OperationIsNotPossibleException("Update not possible");
        else {
            model.setPrefix(model.getPrefix().toUpperCase());
            repository.save(model);
        }
    }
    /**
     * метод по удалению проекта
     * @param model бизнес-модель проекта, которую необходимо удалить
     */
    @Transactional
    @Override
    public void remove(Project model) {
        // если спискок задач у проекта пустой, то удаляем проект; если какие-то задачи есть, то выводим исключение
        if (model.getTasks().size() == 0) {
            // устанавливаем флажок, который означает, что проект удалён
            model.setDeleted(true);
            repository.save(model);
//            repository.delete(model.getId());
        } else
            throw new OperationIsNotPossibleException("Project has tasks. Deleting is not possible");
    }

//    @Override
//    public void createFromTemplate(Object... args) {
//        if (args.length != 6)
//            throw new OperationIsNotPossibleException("Project.createFromTemplate: creating test project is impossible");
//        if (!(args[0] instanceof String))
//            throw new OperationIsNotPossibleException("Project.createFromTemplate: argument 0 must be String");
//        if (!(args[1] instanceof String))
//            throw new OperationIsNotPossibleException("Project.createFromTemplate: argument 1 must be String");
//        if (!(args[2] instanceof String))
//        throw new OperationIsNotPossibleException("Project.createFromTemplate: argument 2 must be String");
//        if (!(args[3] instanceof User))
//            throw new OperationIsNotPossibleException("Project.createFromTemplate: argument 3 must be User");
//        if (!(args[4] instanceof List))
//            throw new OperationIsNotPossibleException("Project.createFromTemplate: argument 4 must be List<Task>");
//        if (!(args[5] instanceof List))
//            throw new OperationIsNotPossibleException("Project.createFromTemplate: argument 5 must be List<TaskType>");
//
//        Project project = new Project();
//        project.setName((String)args[0]);
//        project.setDescription((String)args[1]);
//        project.setPrefix((String)args[2]);
//        project.setOwner((User)args[3]);
//        project.setCreated(ZonedDateTime.now());
//        project.setTasks((List<Task>)args[4]);
//        project.setTaskTypes((List<TaskType>)args[5]);
//        project.setLastTaskCode(0L);
//        project.setDeleted(false);
//
//        repository.save(project);
//    }
}
