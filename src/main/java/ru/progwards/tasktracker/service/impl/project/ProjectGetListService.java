package ru.progwards.tasktracker.service.impl.project;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectGetListService implements GetListService<Project> {
    /**
     * репозиторий с проектами
     */
    ProjectRepository repository;

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