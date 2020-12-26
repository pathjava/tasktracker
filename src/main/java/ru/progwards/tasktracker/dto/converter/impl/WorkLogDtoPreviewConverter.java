package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.WorkLogDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.model.WorkLog;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkLogDtoPreviewConverter implements Converter<WorkLog, WorkLogDtoPreview> {

    private final @NonNull GetService<Long, WorkLog> workLogGetService;
    private final @NonNull Converter<User, UserDtoPreview> userUserDtoConverter;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public WorkLog toModel(WorkLogDtoPreview dto) {
        if (dto == null)
            return null;
        else {
            WorkLog workLog = workLogGetService.get(dto.getId());
            return new WorkLog(
                    dto.getId(),
                    workLog.getTask(),
                    dto.getSpent(),
                    userUserDtoConverter.toModel(dto.getWorker()),
                    dto.getStart(),
                    workLog.getDescription(),
                    workLog.getEstimateChange(),
                    workLog.getEstimateValue()
            );
        }
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public WorkLogDtoPreview toDto(WorkLog model) {
        if (model == null)
            return null;
        else
            return new WorkLogDtoPreview(
                    model.getId(),
                    model.getSpent(),
                    userUserDtoConverter.toDto(model.getWorker()),
                    model.getStart()
            );
    }
}
