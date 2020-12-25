package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.AccessRule;

/**
 * @author Artem Dikov
 */
@Repository
@Transactional(readOnly = true)
public interface AccessRuleRepository extends JpaRepository<AccessRule, Long> {
}
