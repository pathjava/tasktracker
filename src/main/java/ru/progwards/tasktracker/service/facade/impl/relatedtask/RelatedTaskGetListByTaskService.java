package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Бизнес-логика получения связанных задач
 *
 * @author Oleg Kiselev
 */
@Service
public class RelatedTaskGetListByTaskService implements GetListByTaskService<Long, RelatedTask> {

    private RepositoryByTaskId<Long, RelatedTaskEntity> byTaskId;
    private Converter<RelatedTaskEntity, RelatedTask> converter;

    @Autowired
    public void setByTaskId(RepositoryByTaskId<Long, RelatedTaskEntity> byTaskId) {
        this.byTaskId = byTaskId;
    }

    @Autowired
    public void setConverter(Converter<RelatedTaskEntity, RelatedTask> converter) {
        this.converter = converter;
    }

    /**
     * @param taskId идентификатор задачи для которой необходимо получить связанные задачи
     * @return коллекция связанных задач (может иметь пустое значение)
     */
    @Override
    public Collection<RelatedTask> getListByTaskId(Long taskId) {
        return byTaskId.getByTaskId(taskId).stream()
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }
}
