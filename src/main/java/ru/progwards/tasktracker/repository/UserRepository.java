package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Kiselev
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Метод проверки существования email в БД
     *
     * @param email проверяемый электронный адрес
     * @return true - если email есть в БД и false - если нет
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Метод получения пользователя по адресу электронной почты
     *
     * @param email электронный адрес
     * @return пользователь
     */
    Optional<User> findByEmailIgnoreCase(String email);

    /**
     * Метод получения User если его поле deleted имеет значение false
     *
     * @param id идентификатор запрашиваемой User
     * @return User
     */
    Optional<User> findByIdAndDeletedFalse(Long id);

    /**
     * Метод получения листа User у которых поле deleted имеет значение false
     *
     * @return лист User
     */
    List<User> findAllByDeletedFalse();

    /**
     * Метод установки значения поля User deleted как true - отмечаем User как удаленного
     *
     * @param deleted true - отметка, что User удален
     * @param id      идентификатор User, помечаемого как удаленный
     */
    @Modifying
    @Query("UPDATE User u SET u.deleted = :value WHERE u.id = :id")
    void markUserAsDeleted(@Param("value") boolean deleted, @Param("id") Long id);
}
