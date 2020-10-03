package ru.progwards.controller.converter;

public interface Converter<M, D> {
    M toModel(D dto);
    D toDto(M model);
}
