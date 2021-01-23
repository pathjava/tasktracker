package ru.progwards.tasktracker.repository.deprecated;

import java.util.Collection;

@Deprecated
public interface RepositoryByProjectId<T, E> {

    /**
     * Получить список объектов по идентификатору проекта
     *
     * @param projectId идентификатор проекта
     * @return список сущностей {@code E} репозитория
     */
    Collection<E> getByProjectId(T projectId);

}
