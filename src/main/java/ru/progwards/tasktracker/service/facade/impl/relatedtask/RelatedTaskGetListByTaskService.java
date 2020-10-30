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

@Service
public class RelatedTaskGetListByTaskService implements GetListByTaskService<Long, RelatedTask> {

    private RepositoryByTaskId<Long, RelatedTaskEntity> byTaskId;
    private Converter<RelatedTaskEntity, RelatedTask> taskConverter;

    @Autowired
    public void setByTaskId(RepositoryByTaskId<Long, RelatedTaskEntity> byTaskId) {
        this.byTaskId = byTaskId;
    }

    @Autowired
    public void setTaskConverter(Converter<RelatedTaskEntity, RelatedTask> taskConverter) {
        this.taskConverter = taskConverter;
    }

    @Override
    public Collection<RelatedTask> getListByTaskId(Long taskId) {
        return byTaskId.getByTaskId(taskId).stream()
                .map(entity -> taskConverter.toVo(entity))
                .collect(Collectors.toList());
    }
}
