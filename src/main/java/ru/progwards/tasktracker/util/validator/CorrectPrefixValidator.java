package ru.progwards.tasktracker.util.validator;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.dto.ProjectDtoFull;
import ru.progwards.tasktracker.util.validator.annotation.CorrectPrefix;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Класс-обработчик аннотации UniqueAndCorrectPrefix
 * @author Pavel Khovaylo
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CorrectPrefixValidator implements ConstraintValidator<CorrectPrefix, ProjectDtoFull> {
    /**
     * метод сперва проверяет значение свойства prefix объекта ProjectDtoFull на корректность
     * значение свойства prefix не должно содержать другие символы, кроме букв латинского алфавита и цифр
     * значение свойства prefix должно начинаться только с буквы
     * @param projectDtoFull объект, для которого происходит проверка свойства
     * @param context предоставляет контекстные данные и операции при применении заданного валидатора ограничений
     * @return true - если значение свойства уникально, false - если в БД уже содержится объект с таким же значением
     */
    @Override
    public boolean isValid(ProjectDtoFull projectDtoFull, ConstraintValidatorContext context) {
        String prefix = projectDtoFull.getPrefix();
        if (projectDtoFull.getId() == null) {
            if (!isOnlyLatinSymbolsOrDigits(prefix.toLowerCase())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Project's prefix must contain only letters " +
                        "or digits without spaces");
                return false;
            }
            if (!isFirstSymbolIsLetter(prefix.toLowerCase())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Prefix's first symbol is not letter");
                return false;
            }
        }
        return true;
    }
    /**
     * метод проверяет переданную строку на содержание только букв латинского алфавита и цифр
     * @param prefix переданный префикс создаваемого проекта
     * @return true - строка содержит только буквы латинского алфавита и цифры, false - если есть другие символы
     */
    private static boolean isOnlyLatinSymbolsOrDigits(String prefix) {
        return prefix.matches("^[A-Za-z0-9]+$");
    }
    /**
     * метод проверяет является ли первый символ строки буквой
     * @param prefix переданный префикс создаваемого проекта
     * @return true - если первый символ это буква, false - первый символ не является буквой
     */
    private static boolean isFirstSymbolIsLetter(String prefix) {
        char[] chars = prefix.toCharArray();
        return Character.isLetter(chars[0]);
    }
}