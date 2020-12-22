package ru.progwards.tasktracker.service.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.ProjectEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.Project;

/**
 * Класс по получению одного проекта
 * @author Pavel Khovaylo
 */
@Service
public class ProjectGetService implements GetService<Long, Project> {

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
     * метод по получению проекта
     * @param id идентификатор проекта, который необходимо получить
     * @return бизнес-модель проекта
     */
    @Override
    public Project get(Long id) {
        return converter.toVo(repository.get(id));
    }
}