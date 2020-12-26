package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.repository.RelatedTaskRepository;
import ru.progwards.tasktracker.repository.RelationTypeRepository;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;

import java.util.List;

/**
 * Бизнес-логика связанной задачи (RelatedTask)
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RelatedTaskService implements CreateService<RelatedTask>, GetService<Long, RelatedTask>,
        RemoveService<RelatedTask>, GetListService<RelatedTask> {

    private final @NonNull RelatedTaskRepository relatedTaskRepository;
    private final @NonNull RelationTypeRepository relationTypeRepository;

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

        /* old version */
//        if (checkExistTypeAndLink(model.getCurrentTask().getId(), model.getAttachedTask().getId(), model.getRelationType().getId())) {
//            if (model.getRelationType().getCounterRelation() != null) {
//                RelationType counterType = typeGetService.get(model.getRelationType().getCounterRelation().getId());
//                RelatedTask counter = new RelatedTask(
//                        null, counterType, model.getAttachedTask(), model.getCurrentTask()
//                );
//                relatedTaskRepository.save(counter);
//            }
//            relatedTaskRepository.save(model);
//        }
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

    /* old version */
//    /**
//     * Метод проверки существования между двумя задачами связей RelatedTask одного типа RelationType
//     *
//     * @param currentTaskId  идентификатор задачи из которой создается связь
//     * @param attachedTaskId идентификатор задачи на которую создается связь
//     * @param relationTypeId тип связи создаваемой RelatedTask
//     * @return false - если такой тип связи RelationType уже существует в текущей задаче
//     * и true если такого типа связи RelationType нет
//     */
//    public boolean checkExistTypeAndLink(Long currentTaskId, Long attachedTaskId, Long relationTypeId) {
//        Collection<RelatedTask> collection = getListByTaskId(currentTaskId);
//        for (RelatedTask relatedTask : collection) {
//            if (relatedTask.getRelationType().getId().equals(relationTypeId)
//                    && relatedTask.getCurrentTask().getId().equals(attachedTaskId))
//                return false;
//        }
//        return true;
//    }

    /* -- Deprecated -- */
//    /**
//     * Метод получения коллекции связанных задач по идентификатору задачи
//     *
//     * @param taskId идентификатор задачи для которой необходимо получить связанные задачи
//     * @return коллекция связанных задач (может иметь пустое значение)
//     */
//    @Override
//    public Collection<RelatedTask> getListByTaskId(Long taskId) {
//        return byTaskId.getByTaskId(taskId).stream()
//                .map(entity -> converter.toVo(entity))
//                .collect(Collectors.toList());
//    }

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
            RelatedTask counterRelatedTask = relatedTaskRepository
                    .findRelatedTaskByAttachedTaskAndRelationType_CounterRelation(
                            model.getAttachedTask(), model.getRelationType().getCounterRelation()
                    )
                    .orElseThrow(() -> new NotFoundException("counterRelatedTask not found"));

            counterRelatedTask.setDeleted(true);
            relatedTaskRepository.save(counterRelatedTask);
        }
        model.setDeleted(true);
        relatedTaskRepository.save(model);

        /* old version */
//        if (model.getRelationType().getCounterRelation() != null) {
//            Collection<RelatedTask> collection = getListByAttachedTaskId(model.getCurrentTask().getId());
//            if (!collection.isEmpty()) {
//                collection.stream()
//                        .filter(relatedTask -> model.getCurrentTask().getId().equals(relatedTask.getAttachedTask().getId())
//                                && model.getAttachedTask().getId().equals(relatedTask.getCurrentTask().getId()))
//                        .forEach(relatedTask -> repository.delete(relatedTask.getId()));
//            }
//        }
//        model.setDeleted(true);
//        relatedTaskRepository.save(model);
    }

    /* -- Deprecated -- */
//    /**
//     * Метод получения всех входящих RelatedTask для определенной задачи (Task)
//     *
//     * @param taskId идентификатор задачи
//     * @return коллекция value object
//     */
//    @Override
//    public Collection<RelatedTask> getListByAttachedTaskId(Long taskId) {
//        return byAttachedTaskId.getByAttachedTaskId(taskId).stream()
//                .map(entity -> converter.toVo(entity))
//                .collect(Collectors.toList());
//    }
}
