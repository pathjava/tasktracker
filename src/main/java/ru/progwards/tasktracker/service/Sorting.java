package ru.progwards.tasktracker.service;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Получение листа отсортированных объектов
 *
 * @param <E> тип бизнес объекта
 */
public interface Sorting<E> {

    List<E> getListSort(Sort sort);

}

