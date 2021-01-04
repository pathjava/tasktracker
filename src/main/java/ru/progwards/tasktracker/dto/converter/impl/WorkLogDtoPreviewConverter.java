package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.WorkLogDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.model.WorkLog;
import ru.progwards.tasktracker.service.GetService;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkLogDtoPreviewConverter implements Converter<WorkLog, WorkLogDtoPreview> {

    private final GetService<Long, WorkLog> workLogGetService;
    private final Converter<User, UserDtoPreview> userUserDtoConverter;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public WorkLog toModel(WorkLogDtoPreview dto) {
        return workLogGetService.get(dto.getId());
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public WorkLogDtoPreview toDto(WorkLog model) {
        return new WorkLogDtoPreview(
                model.getId(),
                model.getSpent(),
                userUserDtoConverter.toDto(model.getWorker()),
                model.getStart()
        );
    }
}
