package ru.progwards.tasktracker.util.validator;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.util.validator.annotation.UniquePrefix;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Pavel Khovaylo
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UniquePrefixValidator implements ConstraintValidator<UniquePrefix, String> {

   ProjectRepository repository;

   @Override
   public boolean isValid(String value, ConstraintValidatorContext context) {
      return repository.findByPrefix(value).isPresent();
   }
}
