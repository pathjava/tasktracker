package ru.progwards.tasktracker.repository.deprecated;


public interface RepositoryByCode<T, E> {

    /**
     * Получить объект по коду
     *
     * @param code идентификатор
     * @return сущность {@code E} репозитория
     */
    E getByCode(T code);
}
