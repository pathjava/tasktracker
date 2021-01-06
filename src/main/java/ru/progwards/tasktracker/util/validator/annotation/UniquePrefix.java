package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.UniquePrefixValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Pavel Khovaylo
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniquePrefixValidator.class)
public @interface UniquePrefix {

    String message() default "This prefix is in use";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
