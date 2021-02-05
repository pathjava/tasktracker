package ru.progwards.tasktracker.util.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.dto.WorkFlowActionDtoFull;
import ru.progwards.tasktracker.model.WorkFlowAction;
import ru.progwards.tasktracker.repository.WorkFlowActionRepository;
import ru.progwards.tasktracker.util.validator.annotation.WorkFlowActionNameValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор проверки уникальности имени WorkFlowAction перед созданием и обновлением WorkFlowAction
 *
 * @author Oleg Kiselev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkFlowActionNameValidator implements ConstraintValidator<WorkFlowActionNameValid, WorkFlowActionDtoFull> {

    private final WorkFlowActionRepository workFlowActionRepository;

    @Override
    public void initialize(WorkFlowActionNameValid constraintAnnotation) {
    }

    /**
     * Метод проверки существования имени WorkFlowAction в БД перед созданием и обновлением WorkFlowAction
     *
     * @param dtoFull проверяемое имя WorkFlowAction
     * @param context Предоставляет контекстные данные и операции при применении заданного
     * @return false - если в БД есть WorkFlowAction с проверяемым именем и true - если нет
     */
    @Override
    public boolean isValid(WorkFlowActionDtoFull dtoFull, ConstraintValidatorContext context) {
        if (dtoFull.getId() == null) {
            if (workFlowActionRepository.existsByName(dtoFull.getName().toLowerCase().trim())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(dtoFull.getName().toLowerCase().trim() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        } else {
            WorkFlowAction workFlowAction = workFlowActionRepository.findByName(
                    dtoFull.getName().toLowerCase().trim()).orElse(null);
            if (workFlowAction != null && !workFlowAction.getId().equals(dtoFull.getId())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(dtoFull.getName().toLowerCase().trim() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
