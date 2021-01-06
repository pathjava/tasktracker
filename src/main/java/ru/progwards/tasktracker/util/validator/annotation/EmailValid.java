package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.UserEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки существования email в БД при регистрации и обновлении пользователя.
 * Аннотация работает только на уровне класса - @Target(ElementType.TYPE)
 * Для использования в UserDtoFull над классом надо прописать @EmailValid(groups = {Create.class, Update.class}),
 * а в UserController в методе create в параметрах перед @RequestBody прописать @Validated(Create.class),
 * а в методе update перед @RequestBody прописать @Validated(Update.class)
 *
 * @author Oleg Kiselev
 */
@Documented
@Constraint(validatedBy = UserEmailValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValid {

    String message() default "email is not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
