package ru.progwards.tasktracker.util.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.util.validator.annotation.TaskTypeNameValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор проверки уникальности имени TaskType перед созданием и обновлением TaskType
 *
 * @author Oleg Kiselev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskTypeNameValidator implements ConstraintValidator<TaskTypeNameValid, TaskTypeDtoFull> {

    private final TaskTypeRepository taskTypeRepository;

    @Override
    public void initialize(TaskTypeNameValid constraintAnnotation) {
    }

    /**
     * Метод проверки существования имени TaskType в БД перед созданием и обновлением TaskType
     *
     * @param typeDto проверяемое имя TaskType
     * @param context Предоставляет контекстные данные и операции при применении заданного
     * @return false - если в БД есть TaskType с проверяемым именем и true - если нет
     */
    @Override
    public boolean isValid(TaskTypeDtoFull typeDto, ConstraintValidatorContext context) {
        if (typeDto.getId() == null) {
            if (taskTypeRepository.existsByNameIgnoreCase(typeDto.getName().trim())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(typeDto.getName().trim() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        } else {
            TaskType taskType = taskTypeRepository.findByNameIgnoreCase(
                    typeDto.getName().trim()).orElse(null);
            if (taskType != null && !taskType.getId().equals(typeDto.getId())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(typeDto.getName().trim() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
