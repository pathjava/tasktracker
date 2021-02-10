package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.model.TaskType;

import java.util.Optional;

/**
 * @author Oleg Kiselev
 */
@Repository
@Transactional(readOnly = true)
public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {

    /**
     * Метод проверки существования в БД TaskType с именем name
     *
     * @param name имя TaskType
     * @return true - если в БД есть TaskType с проверяемым именем и false - если нет
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Метод получения TaskType по имени
     *
     * @param name имя TaskType
     * @return TaskType
     */
    Optional<TaskType> findByNameIgnoreCase(String name);

    // https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

}
