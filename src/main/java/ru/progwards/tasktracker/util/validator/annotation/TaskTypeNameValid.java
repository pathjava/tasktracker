package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.TaskTypeNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки существования name в БД при регистрации и обновлении TaskTypeDto.
 * Аннотация работает только на уровне класса - @Target(ElementType.TYPE)
 * Для использования в TaskTypeDtoFull над классом надо прописать @TaskTypeNameValid(groups = {Create.class, Update.class}),
 * а в TaskTypeController в методе create в параметрах перед @RequestBody прописать @Validated(Create.class),
 * а в методе update перед @RequestBody прописать @Validated(Update.class)
 *
 * @author Oleg Kiselev
 */
@Documented
@Constraint(validatedBy = TaskTypeNameValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskTypeNameValid {

    String message() default "this name TaskType already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
