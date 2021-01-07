package ru.progwards.tasktracker.service.impl.project;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.service.RefreshService;

/**
 * Класс по обновлению проекта
 * @author Pavel Khovaylo
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectRefreshService implements RefreshService<Project> {
    /**
     * репозиторий с проектами
     */
    ProjectRepository repository;

    /**
     * метод по обновлению проекта
     * @param model бизнес-модель, которую хотим обновить
     */
    @Override
    public void refresh(Project model) {
        Project project = repository.findById(model.getId()).orElseThrow(() ->
                new OperationIsNotPossibleException("Project.id = " + model.getId() + " doesn't exist"));

         //если в обновляемом проекте меняем префикс и у проекта имеются задачи, то обновление невозможно
        if (!model.getPrefix().equals(project.getPrefix()) &&
                model.getTasks().size() > 0)
            throw new OperationIsNotPossibleException("Update not possible");
        else {
            model.setPrefix(model.getPrefix().toUpperCase());
            repository.save(model);
        }
    }
}