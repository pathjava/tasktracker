package ru.progwards.tasktracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Получение объектов страницы пагинации
 *
 * @param <E> тип бизнес объекта
 */
public interface Paging<E> {

    Page<E> getListPageable(Pageable pageable);

}
