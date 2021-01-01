package ru.progwards.tasktracker.util.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.dto.UserDtoFull;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.repository.UserRepository;
import ru.progwards.tasktracker.util.validator.annotation.EmailValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор для проверки существования email в БД при регистрации и обновлении пользователя
 *
 * @author Oleg Kiselev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class UserEmailValidator implements ConstraintValidator<EmailValid, UserDtoFull> {

    private final UserRepository userRepository;

    @Override
    public void initialize(EmailValid emailValid) {
    }

    @Override
    public boolean isValid(UserDtoFull userDto, ConstraintValidatorContext context) {
        if (userDto.getId() == null) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(userDto.getEmail() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        } else {
            User user = userRepository.findByEmail(userDto.getEmail()).orElse(null);
            if (user != null && !user.getId().equals(userDto.getId())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(userDto.getEmail() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
