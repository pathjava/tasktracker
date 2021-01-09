package ru.progwards.tasktracker.service.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.service.*;

import java.util.List;

/**
 * Сервисные методы для объекта Project
 * @author Pavel Khovaylo
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectService implements GetListService<Project>, GetService<Long, Project>, CreateService<Project>, RefreshService<Project>, RemoveService<Project> {
    /**
     * репозиторий с проектами
     */
    ProjectRepository repository;

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
        return repository.findById(id).
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
        model.setPrefix(model.getPrefix().toUpperCase());
        repository.save(model);
    }
    /**
     * метод по обновлению проекта
     * @param model бизнес-модель, которую хотим обновить
     */
    @Transactional
    @Override
    public void refresh(Project model) {
        Project project = repository.findById(model.getId()).orElseThrow(() ->
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
            throw new OperationIsNotPossibleException("Project has the tasks. Delete not possible");
    }
}
