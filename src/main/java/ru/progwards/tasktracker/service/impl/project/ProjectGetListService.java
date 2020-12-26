package ru.progwards.tasktracker.service.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.service.GetListService;

import java.util.List;
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
    private ProjectRepository repository;

    /**
     * метод по получению списка всех проектов
     * @return список всех проектов из репозитория
     */
    @Override
    public List<Project> getList() {
        // получаем список, если isDeleted = false
        return repository.findAll().stream().
                filter(e -> !e.isDeleted()).
                collect(Collectors.toList());
    }
}