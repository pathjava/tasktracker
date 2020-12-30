package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.UserEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки существования prefix в БД при создании проекта
 *
 * @author Oleg Kiselev
 */
@Documented
@Constraint(validatedBy = UserEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrefixValid {

    String message() default "prefix is not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
