package ru.progwards.tasktracker.service.facade.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.controller.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetListByProjectService;
import ru.progwards.tasktracker.service.facade.RefreshService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * Класс по обновлению проекта
 * @author Pavel Khovaylo
 */
@Service
public class ProjectRefreshService implements RefreshService<Project> {

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
     * для получения Task, относящихся к проекту
     */
    @Autowired
    private GetListByProjectService<Long, Task> taskGetListByProjectService;

    /**
     * метод по обновлению проекта
     * @param model бизнес-модель, которую хотим обновить
     */
    @Override
    public void refresh(Project model) {
        Project project = converter.toVo(repository.get(model.getId()));

         //если в обновленном проекте другой префикс и в обновляемом проекте имеются задачи, то обновление невозможно
        if (!model.getPrefix().equals(project.getPrefix()) &&
                taskGetListByProjectService.getListByProjectId(project.getId()).size() > 0)
            throw new OperationIsNotPossibleException("Update not possible");
        else {
            // если LastTaskCode не установлен, то взять значение у предыдущей версии проекта
            if (model.getLastTaskCode() == null)
                model.setLastTaskCode(project.getLastTaskCode());
            repository.update(converter.toEntity(model));
        }

    }
}