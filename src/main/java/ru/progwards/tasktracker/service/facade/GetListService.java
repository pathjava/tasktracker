package ru.progwards.tasktracker.service.facade;

import java.util.Collection;

public interface GetListService<M> {

    Collection<M> getList();

}
