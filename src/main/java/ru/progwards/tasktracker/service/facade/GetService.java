package ru.progwards.tasktracker.service.facade;

public interface GetService<T, M> {

    M get(T id);

}
