package ru.progwards.tasktracker.util.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.dto.RelationTypeDtoFull;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.repository.RelationTypeRepository;
import ru.progwards.tasktracker.util.validator.annotation.RelationTypeNameValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор проверки уникальности имени RelationType перед созданием и обновлением RelationType
 *
 * @author Oleg Kiselev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class RelationTypeNameValidator implements ConstraintValidator<RelationTypeNameValid, RelationTypeDtoFull> {

    private final RelationTypeRepository relationTypeRepository;

    @Override
    public void initialize(RelationTypeNameValid constraintAnnotation) {
    }

    /**
     * Метод проверки существования имени RelationType в БД перед созданием или обновлением RelationType
     *
     * @param typeDto RelationTypeDtoFull
     * @param context Предоставляет контекстные данные и операции при применении заданного
     * @return false - если в БД есть RelationType с проверяемым именем и true - если нет
     */
    @Override
    public boolean isValid(RelationTypeDtoFull typeDto, ConstraintValidatorContext context) {
        if (typeDto.getId() == null) {
            if (relationTypeRepository.existsByNameIgnoreCase(typeDto.getName().trim())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(typeDto.getName().trim() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        } else {
            RelationType relationType = relationTypeRepository.findByNameIgnoreCase(
                    typeDto.getName().trim()).orElse(null);
            if (relationType != null && !relationType.getId().equals(typeDto.getId())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(typeDto.getName().trim() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
