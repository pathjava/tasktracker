package ru.progwards.tasktracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Получение объектов страницы пагинации
 *
 * @param <T> тип идентификатора
 * @param <M> модель бизнес объекта
 */
public interface Paging<T, M> {

    Page<M> getPageableList(Pageable pageable);

    default Page<M> getPageableListById(T id, Pageable pageable) {
        return null;
    }

}
