package ru.progwards.tasktracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.WorkLog;

import java.util.List;

/**
 * @author Oleg Kiselev
 */
@Repository
@Transactional(readOnly = true)
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    /**
     * Метод получения сортированного листа WorkLog по id Task
     *
     * @param id   идентификатор Task
     * @param sort параметр/параметры, по которым происходит сортировка
     * @return лист отсортированных WorkLog задачи
     */
    List<WorkLog> findByTaskId(Long id, Sort sort);

    /**
     * Метод получения страницы пагинации объектов WorkLog по id Task
     *
     * @param id       идентификатор Task
     * @param pageable параметр/параметры по которым происходит выборка страницы пагинации объектов
     * @return страница пагинации объектов WorkLog задачи
     */
    Page<WorkLog> findByTaskId(Long id, Pageable pageable);

}
