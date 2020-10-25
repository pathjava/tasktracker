package ru.progwards.tasktracker.repository.dao;

import java.util.Collection;

public interface RepositoryByTaskId<T, E> {

    Collection<E> getByTaskId(T taskId);

}
