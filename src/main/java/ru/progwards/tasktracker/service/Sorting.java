package ru.progwards.tasktracker.service;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Получение листа отсортированных объектов
 *
 * @param <E> тип бизнес объекта
 */
public interface Sorting<E> {

    List<E> getSortList(Sort sort);

    default List<E> getSortListById(Long id, Sort sort) {
        return null;
    }

}

