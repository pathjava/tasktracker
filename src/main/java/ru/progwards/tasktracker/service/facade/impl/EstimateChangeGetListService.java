package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.util.types.EstimateChange;

import java.util.Collection;
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
     * @return коллекцию строковых значений
     */
    @Override
    public Collection<String> getList() {
        return Stream.of(EstimateChange.values())
                .map(EstimateChange::name)
                .collect(Collectors.toUnmodifiableList());
    }
}
