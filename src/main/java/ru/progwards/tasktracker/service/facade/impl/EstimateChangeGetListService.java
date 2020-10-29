package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.util.types.EstimateChange;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EstimateChangeGetListService implements GetListService<String> {
    @Override
    public Collection<String> getList() {
        return Stream.of(EstimateChange.values())
                .map(EstimateChange::name)
                .collect(Collectors.toUnmodifiableList());
    }
}
