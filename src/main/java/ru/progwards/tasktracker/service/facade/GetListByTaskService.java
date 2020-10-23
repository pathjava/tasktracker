package ru.progwards.tasktracker.service.facade;

import java.util.Collection;

public interface GetListByTaskService<T, M> {

    Collection<M> getListByTaskId(T taskId);

}
