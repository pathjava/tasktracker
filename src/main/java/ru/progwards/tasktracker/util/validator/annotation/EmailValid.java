package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.UserEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки существования email в БД при регистрации пользователя
 *
 * @author Oleg Kiselev
 */
@Documented
@Constraint(validatedBy = UserEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValid {

    String message() default "email is not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
