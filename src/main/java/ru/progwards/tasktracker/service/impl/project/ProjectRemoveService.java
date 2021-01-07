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
import ru.progwards.tasktracker.service.RemoveService;

/**
 * Класс по удалению проекта
 * @author Pavel Khovaylo
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectRemoveService implements RemoveService<Project> {
    /**
     * репозиторий с проектами
     */
    ProjectRepository repository;

    /**
     * метод по удалению проекта
     * @param model бизнес-модель проекта, которую необходимо удалить
     */
    @Override
    public void remove(Project model) {
        // если спискок задач у проекта пустой, то удаляем проект; если какие-то задачи есть, то выводим исключение
        if (model.getTasks().size() == 0) {
            // устанавливаем флажок, который означает, что проект удалён
            model.setDeleted(true);
            repository.save(model);
//            repository.delete(model.getId());
        } else
            throw new OperationIsNotPossibleException("Project has the tasks. Delete not possible");
    }
}