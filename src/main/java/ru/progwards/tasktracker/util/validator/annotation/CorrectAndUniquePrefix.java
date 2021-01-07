package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.CorrectAndUniquePrefixValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Pavel Khovaylo
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CorrectAndUniquePrefixValidator.class)
public @interface CorrectAndUniquePrefix {

    String message() default "This prefix is already in use";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
