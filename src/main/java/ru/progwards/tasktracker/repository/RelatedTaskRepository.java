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
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Kiselev
 */
@Repository
@Transactional(readOnly = true)
public interface RelatedTaskRepository extends JpaRepository<RelatedTask, Long> {

    /**
     * Метод установки значения поля текущей RelatedTask deleted как true - отмечаем RelatedTask как удаленную
     *
     * @param deleted true - отметка, что RelatedTask удалена
     * @param id      идентификатор RelatedTask, помечаемой как удаленная
     */
    @Modifying
    @Query("UPDATE RelatedTask t SET t.deleted = :value WHERE t.id = :id")
    void markRelatedTaskAsDeleted(@Param("value") boolean deleted, @Param("id") Long id);

    /**
     * Метод установки значения поля встречной RelatedTask deleted как true - отмечаем RelatedTask как удаленную
     *
     * @param deleted true - отметка, что RelatedTask удалена
     * @param taskId  идентификатор attachedTask Task которой принадлежит встречная RelatedTask
     * @param typeId  идентификатор RelationType встречной RelatedTask
     */
    @Modifying
    @Query("UPDATE RelatedTask r SET r.deleted = :value WHERE r.currentTask.id = :taskId AND r.relationType.id = :typeId")
    void markCounterRelatedTaskAsDeleted(
            @Param("value") boolean deleted, @Param("taskId") Long taskId, @Param("typeId") Long typeId
    );

    /**
     * Метод проверки существуют ли в Task уже RelatedTask с типом RelationType
     *
     * @param currentTask  текущая Task
     * @param attachedTask встречная (прикрепленная) Task
     * @param relationType тип связи между задачами
     * @return true - если связь типа relationType между currentTask и attachedTask существует,
     * и false - если нет связи такого типа RelationType
     */
    boolean existsRelatedTaskByAttachedTaskAndCurrentTaskAndRelationType(
            Task currentTask, Task attachedTask, RelationType relationType
    );

    /**
     * Метод получения встречной RelatedTask
     *
     * @param attachedTask    attachedTask задача Task на которую указывает RelatedTask
     * @param counterRelation RelationType встречной RelatedTask
     * @return встречная RelatedTask
     */
    Optional<RelatedTask> findRelatedTaskByAttachedTaskAndRelationType_CounterRelation(
            Task attachedTask, RelationType counterRelation
    );

    /**
     * Метод получения RelatedTask если её поле deleted имеет значение false
     *
     * @param id идентификатор запрашиваемой RelatedTask
     * @return RelatedTask
     */
    Optional<RelatedTask> findByIdAndDeletedFalse(Long id);

    /**
     * Метод получения листа RelatedTask у которых поле deleted имеет значение false
     *
     * @return лист RelatedTask
     */
    List<RelatedTask> findAllByDeletedFalse();

    /**
     * Метод проверки существования зави
     *
     * @param relationType RelationType
     * @return true - если RelationType используется в RelatedTask и false - если нет
     */
    boolean existsRelatedTaskByRelationType(RelationType relationType);

    /**
     * Метод получения всех RelatedTask конкретной Task
     *
     * @param currentTask Task у которой надо получить все её RelatedTask
     * @return лист RelatedTask
     */
    List<RelatedTask> findAllByCurrentTask(Task currentTask);

    /**
     * Метод получения сортированного листа RelatedTask по id Task, у которых поле deleted имеет значение false
     *
     * @param id   идентификатор Task
     * @param sort параметр/параметры, по которым происходит сортировка
     * @return лист отсортированных RelatedTask задачи
     */
    List<RelatedTask> findByCurrentTaskIdAndDeletedFalse(Long id, Sort sort);

    /**
     * Метод получения страницы пагинации объектов RelatedTask по id Task у которых поле deleted имеет значение false
     *
     * @param id       идентификатор Task
     * @param pageable параметр/параметры по которым происходит выборка страницы пагинации объектов
     * @return страница пагинации объектов RelatedTask задачи
     */
    Page<RelatedTask> findByCurrentTaskIdAndDeletedFalse(Long id, Pageable pageable);
}
