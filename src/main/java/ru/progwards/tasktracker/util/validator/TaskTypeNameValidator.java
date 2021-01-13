package ru.progwards.tasktracker.util.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.util.validator.annotation.TaskTypeNameValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор проверки уникальности имени TaskType перед созданием TaskType
 *
 * @author Oleg Kiselev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskTypeNameValidator implements ConstraintValidator<TaskTypeNameValid, String> {

    private final TaskTypeRepository taskTypeRepository;

    @Override
    public void initialize(TaskTypeNameValid constraintAnnotation) {
    }

    /**
     * Метод проверки существования имени TaskType в БД перед созданием TaskType
     *
     * @param name    проверяемое имя TaskType
     * @param context Предоставляет контекстные данные и операции при применении заданного
     * @return false - если в БД есть TaskType с проверяемым именем и true - если нет
     */
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (taskTypeRepository.existsByName(name.toLowerCase())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("this " + name.toLowerCase() + " TaskType already exists")
                    .addConstraintViolation();
            return false;
        } else
            return true;
    }
}
