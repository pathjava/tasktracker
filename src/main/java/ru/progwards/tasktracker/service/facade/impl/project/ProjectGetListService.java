package ru.progwards.tasktracker.service.facade.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Project;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Класс по получению списка проектов
 * @author Pavel Khovaylo
 */
@Service
public class ProjectGetListService implements GetListService<Project> {

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
     * метод по получению списка всех проектов
     * @return список всех проектов из репозитория
     */
    @Override
    public Collection<Project> getList() {
        // получаем список, если isDeleted = false
        return repository.get().stream().filter(e -> !e.isDeleted()).
                map(e -> converter.toVo(e)).collect(Collectors.toList());
    }
}