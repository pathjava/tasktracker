package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
