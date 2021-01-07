package ru.progwards.tasktracker.util.validator;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.dto.ProjectDtoFull;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.util.validator.annotation.UniquePrefix;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Pavel Khovaylo
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UniquePrefixValidator implements ConstraintValidator<UniquePrefix, ProjectDtoFull> {

    ProjectRepository repository;

    /**
     * метод позволяет проверить значение свойства prefix объекта ProjectDtoFull на уникальность (т.е. содержит БД или
     * нет объект с данным свойством) при создании и при обновлении объекта
     * @param projectDtoFull объект, для которого происходит проверка свойства
     * @param context предоставляет контекстные данные и операции при применении заданного валидатора ограничений
     * @return true - если значение свойства уникально, false - если в БД уже содержится объект с таким же значением
     */
    @Override
    public boolean isValid(ProjectDtoFull projectDtoFull, ConstraintValidatorContext context) {
        String prefix = projectDtoFull.getPrefix().toUpperCase();
        if (projectDtoFull.getId() == null) {
            if (repository.findByPrefix(prefix).isPresent()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(prefix + " is already in use");
                return false;
            }
        }
        Project model = repository.findByPrefix(prefix).orElse(null);
        if (model != null && !model.getId().equals(projectDtoFull.getId())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(prefix + " is already in use");
            return false;
        }
        return true;
    }
}
