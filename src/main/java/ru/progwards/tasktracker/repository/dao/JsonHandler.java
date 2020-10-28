package ru.progwards.tasktracker.repository.dao;

import java.util.Map;

public interface JsonHandler<T, E> {
    Map<T, E> getMap();
    void write();
    void read();
}
