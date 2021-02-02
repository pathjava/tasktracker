package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.CorrectPrefixValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Pavel Khovaylo
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CorrectPrefixValidator.class)
public @interface CorrectPrefix {

    String message() default "This prefix is not correct";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
