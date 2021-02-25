package ru.progwards.tasktracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.Task;
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
     * Метод установки значения поля Task deleted как true - отмечаем Task как удаленную
     *
     * @param deleted true - отметка, что Task удалена
     * @param id      идентификатор Task, помечаемой как удаленная
     */
    @Modifying
    @Query("UPDATE Task t SET t.deleted = :value WHERE t.id = :id")
    void markTaskAsDeleted(@Param("value") boolean deleted, @Param("id") Long id);

    /**
     * Метод проверки, существуют ли Task с TaskType из параметра метода
     *
     * @param type TaskType
     * @return true или false
     */
    boolean existsTaskByType(TaskType type);

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

    /**
     * Метод проверки существования Task у Project
     *
     * @param id идентификатор Project
     * @return true - если у Project есть Task (даже одна) и false - если нет
     */
    boolean existsTaskByProjectId(Long id);

    List<Task> findByProjectId(Long id, Sort sort);

    Page<Task> findByProjectId(Long id, Pageable pageable);
}
