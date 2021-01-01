package ru.progwards.tasktracker.util.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.util.validator.annotation.PrefixValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор для проверки существования prefix в БД при создании проекта
 *
 * @author Oleg Kiselev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class ProjectPrefixValidator implements ConstraintValidator<PrefixValid, String> {

    private final ProjectRepository projectRepository;

    @Override
    public void initialize(PrefixValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String prefix, ConstraintValidatorContext context) {
        if (projectRepository.existsByPrefix(prefix)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(prefix + " already exists")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
