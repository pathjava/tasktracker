package ru.progwards.tasktracker.repository.deprecated;

import java.util.Collection;

/**
 * Получить список объектов по идентификатору задачи
 *
 * @param <T> идентификатор задачи
 * @param  <E> список сущностей репозитория
 */
public interface RepositoryByAttachedTaskId<T, E> {

    Collection<E> getByAttachedTaskId(T taskId);

}
