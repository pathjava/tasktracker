package ru.progwards.tasktracker.service.facade.impl;

import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

import java.util.Collection;

public class RelatedTaskGetListByTaskService implements GetListByTaskService<Long, RelatedTask> {

    @Override
    public Collection<RelatedTask> getListByTaskId(Long taskId) {
        return null;
    }
}
