package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.Project;

import java.util.List;
import java.util.Optional;

/**
 * @author Pavel Khovaylo
 */
@Repository
@Transactional(readOnly = true)
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Метод получения проекта по префиксу проекта
     *
     * @param prefix префикс проекта
     * @return проект
     */
    Optional<Project> findByPrefix(String prefix);

    /**
     * Метод по получению списка неудаленных проектов
     * @return список проектов, у которых значение свойства deleted = false
     */
    List<Project> getAllByDeletedIsFalse();
}
