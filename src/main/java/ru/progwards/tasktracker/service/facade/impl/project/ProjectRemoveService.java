package ru.progwards.tasktracker.service.facade.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.controller.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.service.vo.Project;

/**
 * Класс по удалению проекта
 * @author Pavel Khovaylo
 */
@Service
public class ProjectRemoveService implements RemoveService<Project> {

    /**
     * репозиторий с проектами
     */
    @Autowired
    private Repository<Long, ProjectEntity> repository;
    /**
     * конвертер проектов
     */
    @Autowired
    private Converter<ProjectEntity, Project> converter;

    /**
     * метод по удалению проекта
     * @param model бизнес-модель проекта, которую необходимо удалить
     */
    @Override
    public void remove(Project model) {
        Project project = converter.toVo(repository.get(model.getId()));

        // если спискок задач у проекта пустой, то удаляем проект; если какие-то задачи есть, то выводим исключение
//        if (project.getTasks().size() == 0)
//            repository.delete(model.getId());
//        else
//            throw new DeletionNotPossibleException("Project with id = " + model.getId() +
//                    " have tasks. Delete not possible");
    }
}