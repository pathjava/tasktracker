package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.WorkFlowAction;

import java.util.Optional;

/**
 * @author Oleg Kiselev
 */
@Repository
@Transactional(readOnly = true)
public interface WorkFlowActionRepository extends JpaRepository<WorkFlowAction, Long> {

    /**
     * Метод проверки существования в БД WorkFlowAction с именем name
     *
     * @param name имя WorkFlowAction
     * @return true - если в БД есть WorkFlowAction с проверяемым именем и false - если нет
     */
    boolean existsByName(String name);

    /**
     * Метод получения WorkFlowAction по имени
     *
     * @param name имя WorkFlowAction
     * @return WorkFlowAction
     */
    Optional<WorkFlowAction> findByName(String name);

    // https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

}
