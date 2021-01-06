package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.ProjectPrefixValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки существования prefix в БД при создании проекта
 * Аннотация работает только на уровне класса - @Target(ElementType.TYPE)
 * Для использования в ProjectDtoFull над классом надо прописать @PrefixValid(groups = {Create.class, Update.class}),
 * а в ProjectController в методе create в параметрах перед @RequestBody прописать @Validated(Create.class),
 * а в методе update перед @RequestBody прописать @Validated(Update.class)
 *
 * @author Oleg Kiselev
 */
@Documented
@Constraint(validatedBy = ProjectPrefixValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrefixValid {

    String message() default "prefix is not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
