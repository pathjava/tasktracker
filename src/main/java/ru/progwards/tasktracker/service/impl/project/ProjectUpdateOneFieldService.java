package ru.progwards.tasktracker.service.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.deprecated.entity.ProjectEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.GetListByProjectService;
import ru.progwards.tasktracker.service.UpdateOneFieldService;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.UpdateOneValue;

/**
 * Класс по обновлению поля у бизнес-модели Project
 * @author Pavel Khovaylo
 */
//TODO думаю НЕ нужна такая возможность в Project (можно убирать эту реализацию)
@Deprecated
public class ProjectUpdateOneFieldService implements UpdateOneFieldService<Project> {

    /**
     * класс, по обновлению поля у сущности ProjectEntity
     */
    @Autowired
    private RepositoryUpdateField<ProjectEntity> projectEntityRepositoryUpdateField;
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
     * метод обновляет поле у бизнес-модели Project
     * @param oneValue объект с данными по текущему обновлению
     */
    @Override
    public void updateOneField(UpdateOneValue oneValue) {
        Project project = converter.toVo(repository.get(oneValue.getId()));

        /* если обновляемое поле называется "lastTaskCode" или если обновляемое поле называется "prefix"
        * и у проекта имеются задачи, то обновление поля невозможно
        */
        if (oneValue.getFieldName().equals("lastTaskCode") ||
                (oneValue.getFieldName().equals("prefix") &&
                        taskGetListByProjectService.getListByProjectId(project.getId()).size() > 0))
            throw new OperationIsNotPossibleException("Update field \"" + oneValue.getFieldName() + "\" not possible");
        else
            projectEntityRepositoryUpdateField.updateField(oneValue);
    }
}