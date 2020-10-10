package ru.progwards.tasktracker.repository.dao;

import java.util.Collection;

public interface Repository<T, E> {

    Collection<E> get();

    E get(T id);

    void create(E elem);

    void update(E elem);

    void delete(T id);

}
