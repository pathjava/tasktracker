package ru.progwards.tasktracker.util.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.UserRepository;
import ru.progwards.tasktracker.util.validator.annotation.EmailValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор для проверки существования email в БД при регистрации пользователя
 *
 * @author Oleg Kiselev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class UserEmailValidator implements ConstraintValidator<EmailValid, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(EmailValid emailValid) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByEmail(email);
    }
}
