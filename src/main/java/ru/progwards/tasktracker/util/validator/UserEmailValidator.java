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
 * Для корректной работы в UserDtoFullConverter в методе toModel
 * поле email надо приводить к нижнему регистру - getEmail().toLowerCase()
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

    /**
     * Метод проверки существования Email в БД при регистрации или обновлении пользователя
     *
     * @param userDto UserDtoFull
     * @param context Предоставляет контекстные данные и операции при применении заданного валидатора ограничений.
     * @return true - если Email отсутствует в БД и false - если есть в БД
     */
    @Override
    public boolean isValid(UserDtoFull userDto, ConstraintValidatorContext context) {
        if (userDto.getId() == null) {
            if (userRepository.existsByEmail(userDto.getEmail().toLowerCase().trim())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(userDto.getEmail().toLowerCase().trim() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        } else {
            User user = userRepository.findByEmail(userDto.getEmail().toLowerCase().trim()).orElse(null);
            if (user != null && !user.getId().equals(userDto.getId())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(userDto.getEmail().toLowerCase().trim() + " already exists")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
