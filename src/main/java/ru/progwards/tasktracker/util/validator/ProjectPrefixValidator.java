package ru.progwards.tasktracker.util.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.dto.ProjectDtoFull;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.util.validator.annotation.PrefixValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор для проверки существования prefix в БД при создании и обновлении проекта
 *
 * @author Oleg Kiselev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class ProjectPrefixValidator implements ConstraintValidator<PrefixValid, ProjectDtoFull> {

    private final ProjectRepository projectRepository;

    @Override
    public void initialize(PrefixValid constraintAnnotation) {
    }

    /**
     * Метод проверки существования префикса проекта при создании или редактировании проекта
     *
     * @param projectDto ProjectDtoFull
     * @param context    Предоставляет контекстные данные и операции при применении заданного валидатора ограничений.
     * @return true - если префикс проекта отсутствует в БД и false - если есть в БД
     */
    @Override
    public boolean isValid(ProjectDtoFull projectDto, ConstraintValidatorContext context) {
        if (projectDto.getId() == null) {
            if (projectRepository.existsByPrefix(projectDto.getPrefix())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(projectDto.getPrefix() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        } else {
            Project project = projectRepository.findByPrefix(projectDto.getPrefix()).orElse(null);
            if (project != null && !project.getId().equals(projectDto.getId())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(projectDto.getPrefix() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
