package ru.progwards.tasktracker.repository.dao;

import java.util.Collection;

public interface Repository<T, E> {

    Collection<E> get();

    E get(T id);

    /**
     * Save new object to repository
     *
     * @param entity object
     */
    void save(E entity);

    /**
     * Update object fields in repository
     * 'id' cannot be updated
     *
     * @param entity object
     */
    void modify(E entity);

    /**
     * Remove object from repository
     *
     * @param entity object
     */
    void delete(E entity);

}
