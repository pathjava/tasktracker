package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.WorkFlowActionNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки существования name в БД при регистрации и обновлении WorkFlowActionDto.
 * Аннотация работает только на уровне класса - @Target(ElementType.TYPE)
 * Для использования в WorkFlowActionDto над классом надо прописать @WorkFlowActionNameValid(groups = {Create.class, Update.class}),
 * а в WorkFlowActionController в методе create в параметрах перед @RequestBody прописать @Validated(Create.class),
 * а в методе update перед @RequestBody прописать @Validated(Update.class)
 *
 * @author Oleg Kiselev
 */
@Documented
@Constraint(validatedBy = WorkFlowActionNameValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkFlowActionNameValid {

    String message() default "this name WorkFlowAction already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
