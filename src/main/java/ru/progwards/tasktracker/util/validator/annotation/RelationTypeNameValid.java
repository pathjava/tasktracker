package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.RelationTypeNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки существования name в БД при регистрации и обновлении RelationType.
 * Аннотация работает только на уровне класса - @Target(ElementType.TYPE)
 * Для использования в RelationTypeDtoFull над классом надо прописать @RelationTypeNameValid(groups = {Create.class, Update.class}),
 * а в RelationTypeController в методе create в параметрах перед @RequestBody прописать @Validated(Create.class),
 * а в методе update перед @RequestBody прописать @Validated(Update.class)
 *
 * @author Oleg Kiselev
 */
@Documented
@Constraint(validatedBy = RelationTypeNameValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RelationTypeNameValid {

    String message() default "this name RelationType already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
