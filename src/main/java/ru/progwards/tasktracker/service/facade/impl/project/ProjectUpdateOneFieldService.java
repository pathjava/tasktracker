package ru.progwards.tasktracker.service.facade.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.controller.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.UpdateOneFieldService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

/**
 * Класс по обновлению поля у бизнес-модели Project
 * @author Pavel Khovaylo
 */
@Service
public class ProjectUpdateOneFieldService implements UpdateOneFieldService {

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
     * метод обновляет поле у бизнес-модели Project
     * @param oneValue объект с данными по текущему обновлению
     */
    @Override
    public void updateOneField(UpdateOneValue oneValue) {
        Project project = converter.toVo(repository.get(oneValue.getId()));

        /* если обновляемое поле называется "lastTaskCode" или если обновляемое поле называется "prefix"
        * и у проекта имеются задачи, то обновление поля невозможно
        */
//        if (oneValue.getFieldName().equals("lastTaskCode") ||
//                (oneValue.getFieldName().equals("prefix") && project.getTasks().size() > 0))
//            throw new UpdateNotPossibleException("Update field \"" + oneValue.getFieldName() + "\" not possible");
//        else
//            projectEntityRepositoryUpdateField.updateField(oneValue);
    }
}