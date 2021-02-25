package ru.progwards.tasktracker.service;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Получение листа отсортированных объектов
 *
 * @param <T> тип идентификатора
 * @param <M> тип бизнес объекта
 */
public interface Sorting<T, M> {

    List<M> getSortList(Sort sort);

    default List<M> getSortListById(T id, Sort sort) {
        return null;
    }

}

