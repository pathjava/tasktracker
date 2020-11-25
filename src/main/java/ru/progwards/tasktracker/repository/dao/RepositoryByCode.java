package ru.progwards.tasktracker.repository.dao;


public interface RepositoryByCode<T, E> {

    /**
     * Получить объект по коду
     *
     * @param code идентификатор
     * @return сущность {@code E} репозитория
     */
    E getByCode(T code);
}
