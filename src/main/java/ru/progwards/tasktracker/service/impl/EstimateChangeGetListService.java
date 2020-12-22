package ru.progwards.tasktracker.service.impl;

import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.model.types.EstimateChange;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Бизнес-логика получения списка вариантов изменения значения продолжительности выполнения задачи
 * при списании затраченного времени
 *
 * @author Oleg Kiselev
 */
@Service
public class EstimateChangeGetListService implements GetListService<String> {

    /**
     * Метод получения коллекции
     *
     * @return коллекцию строковых значений
     */
    @Override
    public List<String> getList() {
        return Stream.of(EstimateChange.values())
                .map(EstimateChange::name)
                .collect(Collectors.toList());
    }

}
