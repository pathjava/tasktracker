package ru.progwards.tasktracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Получение объектов страницы пагинации
 *
 * @param <E> тип бизнес объекта
 */
public interface Paging<E> {

    Page<E> getPageableList(Pageable pageable);

    default Page<E> getPageableListById(Long id, Pageable pageable) {
        return null;
    }

}
