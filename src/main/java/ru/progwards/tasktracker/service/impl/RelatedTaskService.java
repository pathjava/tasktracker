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

import static java.lang.String.format;

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
                        .orElseThrow(() -> new NotFoundException(format("RelationType id=%s not found", id)));
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
                .orElseThrow(() -> new NotFoundException(format("RelatedTask id=%s not found", id)));
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

//    @Transactional
//    @Override
//    public List<RelationType> createFromTemplate(Object... args) {
//        if (args.length != 3)
//            throw new OperationIsNotPossibleException("RelatedTask.createFromTemplate: 3 arguments expected");
//        if (!(args[0] instanceof RelationType[]))
//            throw new OperationIsNotPossibleException("RelatedTask.createFromTemplate: argument 0 must be RelationType");
//        if (!(args[1] instanceof Task[]))
//            throw new OperationIsNotPossibleException("RelatedTask.createFromTemplate: argument 1 must be Task");
//        if (((RelationType[]) args[0]).length != 7)
//            throw new OperationIsNotPossibleException("RelatedTask.createFromTemplate: 7 arguments RelationType expected");
//        if (((Task[]) args[1]).length != 2)
//            throw new OperationIsNotPossibleException("RelatedTask.createFromTemplate: 2 arguments Task expected");
//
//        List<RelationType> relationTypes = new ArrayList<>(Arrays.asList((RelationType[]) args[0]));
//        List<Task> tasks = new ArrayList<>(Arrays.asList((Task[]) args[1]));
//
//        List<RelatedTask> relatedTasks = new ArrayList<>();
//
//        for (RelationType relationType : relationTypes) {
//            RelatedTask relatedTask = new RelatedTask();
//            relatedTask.setRelationType(relationType);
//            relatedTasks.add(relatedTask);
//        }
//
//        for (RelatedTask relatedTask : relatedTasks) {
//            if (relatedTask.getRelationType().getCounterRelation() == null){
//                relatedTask.setCurrentTask(tasks.get(0));
//                relatedTask.setAttachedTask(tasks.get(1));
//            } else {
//
//            }
//        }
//    }
}
