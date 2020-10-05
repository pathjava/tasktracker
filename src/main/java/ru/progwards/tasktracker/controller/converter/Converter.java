package ru.progwards.tasktracker.controller.converter;

public interface Converter<M, D> {

    M toModel(D dto);

    D toDto(M model);

}
