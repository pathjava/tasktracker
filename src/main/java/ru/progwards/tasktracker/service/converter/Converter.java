package ru.progwards.tasktracker.service.converter;

public interface Converter<E, V> {

    V toVo(E entity);

    E toEntity(V valueObject);

}
