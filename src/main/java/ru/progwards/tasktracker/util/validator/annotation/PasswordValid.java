package ru.progwards.tasktracker.util.validator.annotation;

import ru.progwards.tasktracker.util.validator.UserPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация для проверки правильности введенного пароля пользователя при регистрации и обновлении пользователя.
 * Аннотация работает только на уровне класса - @Target(ElementType.TYPE)
 * Для использования в UserDtoFull над классом надо прописать @PasswordValid(groups = {Create.class, Update.class}),
 * а в UserController в методе create в параметрах перед @RequestBody прописать @Validated(Create.class),
 * а в методе update перед @RequestBody прописать @Validated(Update.class)
 *
 * @author Oleg Kiselev
 */
@Documented
@Constraint(validatedBy = UserPasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {

    String message() default "passwords don't match.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
