package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByAttachedTaskId;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Бизнес-логика связанной задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class RelatedTaskService implements CreateService<RelatedTask>, GetService<Long, RelatedTask>,
        GetListByTaskService<Long, RelatedTask>, RemoveService<RelatedTask>,
        GetListService<RelatedTask>, GetListByAttachedTaskId<Long, RelatedTask> {

    @Autowired
    private Repository<Long, RelatedTaskEntity> repository;
    @Autowired
    private RepositoryByTaskId<Long, RelatedTaskEntity> byTaskId;
    @Autowired
    private RepositoryByAttachedTaskId<Long, RelatedTaskEntity> byAttachedTaskId;
    @Autowired
    private Converter<RelatedTaskEntity, RelatedTask> converter;
    @Autowired
    private GetService<Long, RelationType> typeGetService;

    /**
     * Метод создания связанной задачи
     * Первоначально в методе проверяется существование между двумя задачами связей одного типа,
     * и если связь такого типа как в пришедшей в параметре метода create существует,
     * новая связь не добавляется.
     * <p>
     * Далее проверяется, если getCounterRelationId() != null, тогда создается встречная связь
     *
     * @param model value object - объект бизнес логики, который необходимо создать
     */
    @Override
    public void create(RelatedTask model) {

        if (checkExistTypeAndLink(model.getCurrentTask().getId(), model.getAttachedTask().getId(), model.getRelationType().getId())) {
            if (model.getRelationType().getCounterRelation() != null) {
                RelationType counterType = typeGetService.get(model.getRelationType().getCounterRelation().getId());
                RelatedTask counter = new RelatedTask(
                        null, counterType, model.getAttachedTask(), model.getCurrentTask()
                );

                RelatedTaskEntity entity = converter.toEntity(counter);
                repository.create(entity);
                counter.setId(entity.getId());
            }
            RelatedTaskEntity entity = converter.toEntity(model);
            repository.create(entity);
            model.setId(entity.getId());
        }
    }

    /**
     * Метод проверки существования между двумя задачами связей одного типа
     *
     * @param currentTaskId  идентификатор задачи из которой создается связь
     * @param attachedTaskId идентификатор задачи на которую создается связь
     * @param relationTypeId тип связи создаваемой RelatedTask
     * @return false - если такой тип связи RelationType уже существует в текущей задаче
     * и true если такого типа связи RelationType нет
     */
    public boolean checkExistTypeAndLink(Long currentTaskId, Long attachedTaskId, Long relationTypeId) {
        Collection<RelatedTask> collection = getListByTaskId(currentTaskId);
        for (RelatedTask relatedTask : collection) {
            if (relatedTask.getRelationType().getId().equals(relationTypeId)
                    && relatedTask.getCurrentTask().getId().equals(attachedTaskId))
                return false;
        }
        return true;
    }

    /**
     * Метод получения коллекции связанных задач по идентификатору задачи
     *
     * @param taskId идентификатор задачи для которой необходимо получить связанные задачи
     * @return коллекция связанных задач (может иметь пустое значение)
     */
    @Override
    public Collection<RelatedTask> getListByTaskId(Long taskId) {
        return byTaskId.getByTaskId(taskId).stream()
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод получения связанной задачи по её идентификатору
     *
     * @param id идентификатор по которому необходимо получить связанную задачу
     * @return найденную связанную задачу или пусто
     */
    @Override
    public RelatedTask get(Long id) {
        return id == null ? null : converter.toVo(repository.get(id));
    }

    /**
     * Метод получения всех связанных задач без привязки к определенной задаче
     *
     * @return коллекция всех связей задач
     */
    @Override
    public Collection<RelatedTask> getList() {
        return repository.get().stream()
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод удаления связанной задачи
     * Если getCounterRelationId() != null, тогда удаляется встречная связь
     *
     * @param model value object - объект бизнес логики, который необходимо удалить
     */
    @Override
    public void remove(RelatedTask model) {
        if (model.getRelationType().getCounterRelation() != null) {
            Collection<RelatedTask> collection = getListByAttachedTaskId(model.getCurrentTask().getId());
            if (!collection.isEmpty()) {
                collection.stream()
                        .filter(relatedTask -> model.getCurrentTask().getId().equals(relatedTask.getAttachedTask().getId())
                                && model.getAttachedTask().getId().equals(relatedTask.getCurrentTask().getId()))
                        .forEach(relatedTask -> repository.delete(relatedTask.getId()));
            }
        }
        repository.delete(model.getId());
    }

    /**
     * Метод получения всех входящих RelatedTask для определенной задачи (Task)
     *
     * @param taskId идентификатор задачи
     * @return коллекция value object
     */
    @Override
    public Collection<RelatedTask> getListByAttachedTaskId(Long taskId) {
        return byAttachedTaskId.getByAttachedTaskId(taskId).stream()
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }
}
