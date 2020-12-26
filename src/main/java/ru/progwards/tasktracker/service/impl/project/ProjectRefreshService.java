package ru.progwards.tasktracker.service.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.service.RefreshService;

import java.util.List;

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
    private ProjectRepository repository;

    /**
     * метод по обновлению проекта
     * @param model бизнес-модель, которую хотим обновить
     */
    @Override
    public void refresh(Project model) {
        Project project = repository.findById(model.getId()).orElseThrow(() ->
                new OperationIsNotPossibleException("Project.id = " + model.getId() + " doesn't exist"));

        List<Project> projectList = repository.findAll();

        boolean isExists = false;

        for (Project p : projectList) {
            if (p.getPrefix().equals(model.getPrefix())) {
                isExists = true;
                break;
            }
        }

        if (isExists)
            throw new OperationIsNotPossibleException("Update not possible");

         //если в обновленном проекте другой префикс и в обновляемом проекте имеются задачи, то обновление невозможно
        if (!model.getPrefix().equals(project.getPrefix()) &&
                project.getTasks().size() > 0)
            throw new OperationIsNotPossibleException("Update not possible");
        else {
            // если LastTaskCode не установлен, то взять значение у предыдущей версии проекта
            if (model.getLastTaskCode() == null)
                model.setLastTaskCode(project.getLastTaskCode());
            repository.save(model);
        }

    }
}