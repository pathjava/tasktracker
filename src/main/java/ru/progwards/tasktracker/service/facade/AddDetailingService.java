package ru.progwards.tasktracker.service.facade;

import java.util.Collection;

public interface AddDetailingService<V, D> {
    void addDetailing(V valueObject, Collection<? extends D> detailing);
}
