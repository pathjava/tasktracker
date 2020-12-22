package ru.progwards.tasktracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class UserService implements CreateService<User>, RemoveService<User>, GetService<Long, User>, RefreshService<User>, GetListService<User> {

    @Autowired
    private Repository<Long, UserEntity> userRepository;
    @Autowired
    private Converter<UserEntity, User> userConverter;

    /**
     * Создание нового User
     *
     * @param user новый User
     */
    @Override
    public void create(User user) {
        UserEntity entity = userConverter.toEntity(user);
        userRepository.create(entity);
        user.setId(entity.getId());
    }

    /**
     * Удаление User
     *
     * @param user удаляемый User
     */
    @Override
    public void remove(User user) {
        userRepository.delete(user.getId());
    }

    /**
     * Получить информацию по User
     *
     * @param id идентификатор User
     * @return User
     */
    @Override
    public User get(Long id) {
        return userConverter.toVo(userRepository.get(id));
    }


    /**
     * Обновить поля User
     *
     * @param user измененный User
     */
    @Override
    public void refresh(User user) {
        userRepository.update(userConverter.toEntity(user));
    }

    /**
     * Получить список всех User
     *
     * @return список User
     */
    @Override
    public Collection<User> getList() {
        // получили список сущностей
        Collection<UserEntity> UserEntities = userRepository.get();
        List<User> Users = new ArrayList<>(UserEntities.size());
        // преобразуем к бизнес-объектам
        for (UserEntity entity : UserEntities) {
            Users.add(userConverter.toVo(entity));
        }
        return Users;
    }
}
