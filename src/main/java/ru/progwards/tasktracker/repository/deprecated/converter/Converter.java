package ru.progwards.tasktracker.repository.deprecated.converter;

public interface Converter<E, V> {
    V toVo(E entity);
    E toEntity(V valueObject);
}