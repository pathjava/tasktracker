package ru.progwards.tasktracker.service.facade.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.controller.exception.CreatedIsNotPossibleException;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Project;

import java.util.Collection;

/**
 * Класс по созданию бизнес-модели Project
 * @author Pavel Khovaylo
 */
@Service
public class ProjectCreateService implements CreateService<Project> {

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
     * метот добавляет проект в репозиторий
     * @param model бизнес-модель
     */
    @Override
    public void create(Project model) {
        Collection<ProjectEntity> projectEntities = repository.get();

        boolean isExist = false;

        //если в списке проектов уже имеется проект с данным префиксом, то создание нового проекта невозможно
        for (ProjectEntity projectEntity : projectEntities) {
            if (projectEntity.getPrefix().equals(model.getPrefix())) {
                isExist = true;
                break;
            }
        }

        if (isExist)
            throw new CreatedIsNotPossibleException("Create not possible");
        else
            repository.create(converter.toEntity(model));
    }
}