package ru.progwards.tasktracker.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Создание бизнес-объекта
 *
 * @param <M> тип бизнес-объекта
 */
public interface CreateService<M> {

//    @Transactional
    void create(M model);

}
