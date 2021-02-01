package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.repository.RelatedTaskRepository;
import ru.progwards.tasktracker.repository.RelationTypeRepository;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Бизнес-логика связанной задачи (RelatedTask)
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class RelatedTaskService implements CreateService<RelatedTask>, GetService<Long, RelatedTask>,
        RemoveService<RelatedTask>, GetListService<RelatedTask> {

    private final RelatedTaskRepository relatedTaskRepository;
    private final RelationTypeRepository relationTypeRepository;

    /**
     * Метод создания связанной задачи RelatedTask
     * Первоначально в методе проверяется наличие между двумя задачами Task связей RelatedTask одного типа RelationType,
     * и если есть связь такого типа RelationType как в пришедшей RelatedTask в параметре метода create,
     * новая связь не добавляется.
     * Далее проверяется, если getCounterRelationId() != null, тогда создается встречная связь
     *
     * @param model value object - объект бизнес логики, который необходимо создать
     */
    @Transactional
    @Override
    public void create(RelatedTask model) {
        if (!checkingExistenceLinks(model)) {
            if (model.getRelationType().getCounterRelation() != null) {
                Long id = model.getRelationType().getCounterRelation().getId();
                RelationType counterType = relationTypeRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("RelationType id=" + id + " not found"));
                RelatedTask counter = new RelatedTask(
                        null, counterType, model.getAttachedTask(), model.getCurrentTask(), false
                );
                relatedTaskRepository.save(counter);
            }
            relatedTaskRepository.save(model);
        }
    }

    /**
     * Метод проверки существования между двумя задачами связей RelatedTask одного типа RelationType
     *
     * @param model создаваемая RelatedTask из метода create
     * @return true - если такой тип связи RelationType уже существует в текущей задаче
     * и false если такого типа связи RelationType нет
     */
    private boolean checkingExistenceLinks(RelatedTask model) {
        return relatedTaskRepository.existsRelatedTaskByAttachedTaskAndCurrentTaskAndRelationType(
                model.getCurrentTask(), model.getAttachedTask(), model.getRelationType()
        );
    }

    /**
     * Метод получения связанной задачи RelatedTask по её идентификатору
     *
     * @param id идентификатор по которому необходимо получить связанную задачу
     * @return найденную связанную задачу или пусто
     */
    @Override
    public RelatedTask get(Long id) {
        return relatedTaskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("RelatedTask id=" + id + " not found"));
    }

    /**
     * Метод получения всех связанных задач RelatedTask без привязки к определенной задаче
     *
     * @return коллекция всех связей задач
     */
    @Override
    public List<RelatedTask> getList() {
        return relatedTaskRepository.findAllByDeletedFalse();
    }

    /**
     * Метод удаления связанной задачи RelatedTask
     * Если getCounterRelationId() != null, тогда удаляется встречная связь
     *
     * @param model value object - объект бизнес логики, который необходимо удалить
     */
    @Transactional
    @Override
    public void remove(RelatedTask model) {
        if (model.getRelationType().getCounterRelation() != null) {
            relatedTaskRepository.markCounterRelatedTaskAsDeleted(
                    true, model.getAttachedTask().getId(), model.getRelationType().getCounterRelation().getId()
            );
        }
        relatedTaskRepository.markRelatedTaskAsDeleted(true, model.getId());
    }

}
