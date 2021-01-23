package ru.progwards.tasktracker.repository.deprecated;

import java.util.Collection;

@Deprecated
public interface RepositoryByParentId<T, E> {

    /**
     * Получить список объектов по идентификатору родителя
     *
     * @param parentId идентификатор родителя
     * @return список сущностей репозитория
     */
    Collection<E> getByParentId(T parentId);

}
