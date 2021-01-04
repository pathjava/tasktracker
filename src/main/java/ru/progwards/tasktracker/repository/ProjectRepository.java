package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.Project;

/**
 * @author Pavel Khovaylo
 */
@Repository
@Transactional(readOnly = true)
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Метод проверки существования prefix в БД
     *
     * @param prefix проверяемый префикс проекта
     * @return true - если prefix есть в БД и false - если нет
     */
    boolean existsProjectByPrefix(String prefix);

}
