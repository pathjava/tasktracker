package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.repository.UserRepository;
import ru.progwards.tasktracker.service.*;

import java.util.List;

import static java.lang.String.format;

/**
 * Бизнес-логика работы с пользователями
 *
 * @author Aleksandr Sidelnikov, Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class UserService implements CreateService<User>, RemoveService<User>, GetService<Long, User>,
        RefreshService<User>, GetListService<User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Создание нового User
     *
     * @param model новый User
     */
    @Transactional
    @Override
    public void create(User model) {
//        model.setPassword(passwordEncoder.encode(model.getPassword()));
        userRepository.save(model);
    }

    /**
     * Удаление User
     *
     * @param model удаляемый User
     */
    @Transactional
    @Override
    public void remove(User model) {
        userRepository.markUserAsDeleted(true, model.getId());
    }

    /**
     * Получить информацию по User
     *
     * @param id идентификатор User
     * @return User
     */
    @Override
    public User get(Long id) {
        return userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(format("User id=%s not found", id)));
    }


    /**
     * Обновить поля User
     *
     * @param model измененный User
     */
    @Transactional
    @Override
    public void refresh(User model) {
        //TODO - если пароль пуст, то пароль оставляем прежним
//        String password = passwordEncoder.encode(model.getPassword());
//        if (!password.equals(get(model.getId()).getPassword())) {
//            model.setPassword(password);
//        }
        userRepository.save(model);
    }

    /**
     * Получить список всех User
     *
     * @return список User
     */
    @Override
    public List<User> getList() {
        return userRepository.findAllByDeletedFalse();
    }
}
