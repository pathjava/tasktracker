package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.util.types.TaskType;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskTypeGetListService implements GetListService<String> {
    @Override
    public Collection<String> getList() {
        return Stream.of(TaskType.values())
                .map(TaskType::name)
                .collect(Collectors.toUnmodifiableList());
    }
}
