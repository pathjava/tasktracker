package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.repository.UserRepository;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.UserEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Бизнес-логика работы с пользователями
 *
 * @author Aleksandr Sidelnikov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements CreateService<User>, RemoveService<User>, GetService<Long, User>, RefreshService<User>, GetListService<User> {

    private final @NonNull UserRepository userRepository;

    /**
     * Создание нового User
     *
     * @param user новый User
     */
    @Transactional
    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    /**
     * Удаление User
     *
     * @param user удаляемый User
     */
    @Transactional
    @Override
    public void remove(User user) {
        userRepository.delete(user);
    }

    /**
     * Получить информацию по User
     *
     * @param id идентификатор User
     * @return User
     */
    @Override
    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id=" + id + " not found"));
    }


    /**
     * Обновить поля User
     *
     * @param user измененный User
     */
    @Transactional
    @Override
    public void refresh(User user) {
        userRepository.save(user);
    }

    /**
     * Получить список всех User
     *
     * @return список User
     */
    @Override
    public List<User> getList() {
        return userRepository.findAll();
    }
}
