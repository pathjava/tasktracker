package ru.progwards.tasktracker.util.validator;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.dto.ProjectDtoFull;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.util.validator.annotation.CorrectAndUniquePrefix;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Класс-обработчик аннотации UniqueAndCorrectPrefix
 * @author Pavel Khovaylo
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CorrectAndUniquePrefixValidator implements ConstraintValidator<CorrectAndUniquePrefix, ProjectDtoFull> {

    ProjectRepository repository;

    /**
     * 1. метод сперва проверяет значение свойства prefix объекта ProjectDtoFull на корректность
     * 1.1. значение свойства prefix не должно содержать другие символы, кроме букв латинского алфавита и цифр
     * 1.2. значение свойства prefix должно начинаться только с буквы
     * 2. затем метод позволяет проверить значение свойства prefix объекта ProjectDtoFull на уникальность
     * (т.е. содержит БД или нет объект с данным свойством) при создании и при обновлении объекта
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
            if (repository.findByPrefix(prefix.toUpperCase()).isPresent()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(prefix + " already exists");
                return false;
            }
        }
        Project model = repository.findByPrefix(prefix.toUpperCase()).orElse(null);
        if (model != null && !model.getId().equals(projectDtoFull.getId())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(prefix + " is already in use");
            return false;
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