package ru.progwards.service.facade;

import java.util.Collection;

public interface GetListService<M> {
    Collection<M> getAll();
}
