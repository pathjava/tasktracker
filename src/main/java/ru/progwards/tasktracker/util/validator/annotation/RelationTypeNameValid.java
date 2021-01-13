package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.RelationTypeNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Oleg Kiselev
 */
@Documented
@Constraint(validatedBy = RelationTypeNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RelationTypeNameValid {

    String message() default "this name RelationType already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
