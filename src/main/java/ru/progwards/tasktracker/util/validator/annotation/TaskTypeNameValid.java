package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.TaskTypeNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Oleg Kiselev
 */
@Documented
@Constraint(validatedBy = TaskTypeNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskTypeNameValid {

    String message() default "this name TaskType already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
