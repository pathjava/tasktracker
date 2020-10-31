package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.util.types.TaskType;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * получение списка типов задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskTypeGetListService implements GetListService<String> {
    /**
     * @return возвращает коллекцию строковых значений типов задачи
     */
    @Override
    public Collection<String> getList() {
        return Stream.of(TaskType.values())
                .map(TaskType::name)
                .collect(Collectors.toUnmodifiableList());
    }
}
