package ru.progwards.tasktracker.repository.deprecated;

import java.util.Collection;

public interface RepositoryByTaskId<T, E> {

    /**
     * Получить список объектов по идентификатору задачи
     *
     * @param taskId идентификатор задачи
     * @return список сущностей репозитория
     */
    Collection<E> getByTaskId(T taskId);

}
