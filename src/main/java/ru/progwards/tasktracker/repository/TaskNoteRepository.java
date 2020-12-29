package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.TaskNote;

/**
 * Методы работы сущности с базой данных
 *
 * @author Konstantin Kishkin
 */
@Repository
@Transactional(readOnly = true)
public interface TaskNoteRepository extends JpaRepository<TaskNote, Long> {

}
