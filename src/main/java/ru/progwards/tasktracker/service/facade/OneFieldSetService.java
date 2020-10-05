package ru.progwards.tasktracker.service.facade;

public interface OneFieldSetService<M> {

    void setOneField(M model, String fieldName);

}
