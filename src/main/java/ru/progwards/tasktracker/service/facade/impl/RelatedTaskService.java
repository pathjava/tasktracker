package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
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
        GetListByTaskService<Long, RelatedTask>, RemoveService<RelatedTask> {

    @Autowired
    private Repository<Long, RelatedTaskEntity> repository;

    @Autowired
    private RepositoryByTaskId<Long, RelatedTaskEntity> byTaskId;

    @Autowired
    private Converter<RelatedTaskEntity, RelatedTask> relatedTaskConverter;

    @Autowired
    private GetService<Long, RelationType> typeGetService;

    /**
     * Метод создания связанной задачи
     *
     * @param model value object - объект бизнес логики, который необходимо создать
     */
    @Override
    public void create(RelatedTask model) {
        Long counterTypeId = model.getRelationType().getCounterRelationId();
        RelationType counterType = counterTypeId != null ? typeGetService.get(counterTypeId) : null;

        RelatedTask counterRelatedTask = new RelatedTask(
                null, counterType, model.getAttachedTaskId(), model.getCurrentTaskId()
        );
        repository.create(relatedTaskConverter.toEntity(counterRelatedTask));

        repository.create(relatedTaskConverter.toEntity(model));
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
                .map(entity -> relatedTaskConverter.toVo(entity))
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
        return id == null ? null : relatedTaskConverter.toVo(repository.get(id));
    }

    /**
     * Метод удаления связанной задачи
     *
     * @param model value object - объект бизнес логики, который необходимо удалить
     */
    @Override
    public void remove(RelatedTask model) {
        repository.delete(model.getId());
    }
}
