package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.WorkLog;

/**
 * @author Oleg Kiselev
 */
@Repository
@Transactional(readOnly = true)
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
}
