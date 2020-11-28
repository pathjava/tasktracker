package ru.progwards.tasktracker.service.facade.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.controller.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByProjectService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.TaskType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
     * для создания TaskType
     */
    @Autowired
    private CreateService<TaskType> taskTypeCreateService;
    /**
     * класс с методом для получения списка TaskType для данного проекта
     */
    @Autowired
    private GetListByProjectService<Long, TaskType> taskTypeGetListByProjectService;

    /**
     * метот добавляет проект в репозиторий
     * @param model бизнес-модель
     */
    @Override
    public void create(Project model) {
        Collection<ProjectEntity> projectEntities = repository.get();

        boolean isExist = false;

        //если в списке проектов уже имеется проект с данным префиксом, то создание нового проекта невозможно
        if (projectEntities.size() != 0) {
            for (ProjectEntity projectEntity : projectEntities) {
                if (projectEntity.getPrefix().equals(model.getPrefix())) {
                    isExist = true;
                    break;
                }
            }
        }

        if (isExist)
            throw new OperationIsNotPossibleException("Create not possible");
        else {
            //создаем список TaskType проекта
            List<TaskType> taskTypeList = new ArrayList<>(List.of(
                    new TaskType(model.getId(), null, "EPIC"),
                    new TaskType(model.getId(), null, "TASK"),
                    new TaskType(model.getId(), null, "BUG")
                )
            );

            //добавляем TaskType в базу данных
            taskTypeList.forEach(e -> taskTypeCreateService.create(e));

            model.setTaskTypes(taskTypeList);

            repository.create(converter.toEntity(model));
        }
    }
}