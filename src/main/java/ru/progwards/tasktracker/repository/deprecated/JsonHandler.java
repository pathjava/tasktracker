package ru.progwards.tasktracker.repository.deprecated;

import java.util.Map;

public interface JsonHandler<T, E> {
    Map<T, E> getMap();
    void write();
    void read();
}
