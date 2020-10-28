package ru.progwards.tasktracker.repository.dao;

public interface RepositoryByTaskCode<T, E> {
    E getByCode(T elem);
}
