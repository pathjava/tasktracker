package ru.progwards.tasktracker.repository.converter;

public interface Converter<E, V> {
    V toVo(E entity);
    E toEntity(V valueObject);
}