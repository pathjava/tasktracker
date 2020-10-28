package ru.progwards.tasktracker.repository.dao;

import java.util.Collection;

public interface RepositoryByParentId<T, E> {

    /**
     * Получить список объектов по идентификатору родителя
     *
     * @param parentId идентификатор родителя
     * @return список сущностей репозитория
     */
    Collection<E> getByParentId(T parentId);

}