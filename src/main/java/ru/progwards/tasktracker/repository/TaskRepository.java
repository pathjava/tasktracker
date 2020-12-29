package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskNote;
import ru.progwards.tasktracker.model.TaskType;

import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Kiselev
 */
@Repository
@Transactional(readOnly = true)
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Метод проверки, существуют ли Task с TaskType из параметра метода
     *
     * @param type TaskType
     * @return true или false
     */
    boolean existsTaskByType(TaskType type);


    boolean existsTaskByTaskNote(TaskNote taskNote);

    /**
     * Метод получения Task по идентификатору если её поле deleted имеет значение false
     *
     * @param id идентификатор запрашиваемой Task
     * @return Task
     */
    Optional<Task> findByIdAndDeletedFalse(Long id);

    /**
     * Метод получения листа Task у которых поле deleted имеет значение false
     *
     * @return лист Task
     */
    List<Task> findAllByDeletedFalse();

    /**
     * Метод получения Task по коду если её поле deleted имеет значение false
     *
     * @param code текстовый код запрашиваемой Task
     * @return Task
     */
    Optional<Task> findByCodeAndDeletedFalse(String code);
}
