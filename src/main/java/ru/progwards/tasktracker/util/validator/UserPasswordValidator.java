package ru.progwards.tasktracker.util.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.dto.UserDtoFull;
import ru.progwards.tasktracker.util.validator.annotation.PasswordValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор для проверки правильности ввода пароля при регистрации и обновлении пользователя
 *
 * @author Oleg Kiselev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class UserPasswordValidator implements ConstraintValidator<PasswordValid, UserDtoFull> {
    @Override
    public void initialize(PasswordValid constraintAnnotation) {

    }

    /**
     * Метод проверки равенства введенных паролей при регистрации или обновлении пользователя
     *
     * @param userDto UserDtoFull
     * @param context Предоставляет контекстные данные и операции при применении заданного валидатора ограничений.
     * @return true - если пароли одинаковые и false - если разные
     */
    @Override
    public boolean isValid(UserDtoFull userDto, ConstraintValidatorContext context) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("passwords don't match")
                    .addConstraintViolation();
            return false;
        } else
            return true;
    }
}
