package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.WorkFlowStatus;

/**
 * @author Gregory Lobkov
 */
@Repository
@Transactional(readOnly = true)
public interface WorkFlowStatusRepository extends JpaRepository<WorkFlowStatus, Long> {

}
