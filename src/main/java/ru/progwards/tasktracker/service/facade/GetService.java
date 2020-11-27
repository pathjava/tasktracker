package ru.progwards.tasktracker.service.facade;

/**
 * Получить бизнес-объект из хранилища
 *
 * @param <T> тип идентификатора
 * @param <M> тиб бизнес-объекта
 */
public interface GetService<T, M> {

    M get(T id);

}
