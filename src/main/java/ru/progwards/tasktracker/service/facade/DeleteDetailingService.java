package ru.progwards.tasktracker.service.facade;

import java.util.Collection;

public interface DeleteDetailingService<V, D> {
    void deleteDetailing(V valueObject, Collection<? extends D> detailing);
}
