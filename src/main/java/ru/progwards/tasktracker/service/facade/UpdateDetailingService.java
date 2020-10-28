package ru.progwards.tasktracker.service.facade;

import java.util.Collection;

public interface UpdateDetailingService<V, D> {
    void updateDetailing(V valueObject, Collection<? extends D> detailing);
}
