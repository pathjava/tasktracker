package ru.progwards.tasktracker.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.WorkFlow;

import java.util.Optional;

/**
 * @author Gregory Lobkov
 */
@Repository
@Transactional(readOnly = true)
public interface WorkFlowRepository extends JpaRepository<WorkFlow, Long> {

}
