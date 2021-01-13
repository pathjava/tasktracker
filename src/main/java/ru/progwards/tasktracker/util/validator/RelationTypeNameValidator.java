package ru.progwards.tasktracker.util.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.RelationTypeRepository;
import ru.progwards.tasktracker.util.validator.annotation.RelationTypeNameValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор проверки уникальности имени RelationType перед созданием RelationType
 *
 * @author Oleg Kiselev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class RelationTypeNameValidator implements ConstraintValidator<RelationTypeNameValid, String> {

    private final RelationTypeRepository relationTypeRepository;

    @Override
    public void initialize(RelationTypeNameValid constraintAnnotation) {
    }

    /**
     * Метод проверки существования имени RelationType в БД перед созданием RelationType
     *
     * @param name    проверяемое имя RelationType
     * @param context Предоставляет контекстные данные и операции при применении заданного
     * @return false - если в БД есть RelationType с проверяемым именем и true - если нет
     */
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (relationTypeRepository.existsByName(name.toLowerCase())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("this " + name.toLowerCase() + " RelationType already exists")
                    .addConstraintViolation();
            return false;
        } else
            return true;
    }
}
