package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserDto;
import ru.progwards.tasktracker.controller.dto.WorkLogDto;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkLog;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class WorkLogDtoConverter implements Converter<WorkLog, WorkLogDto> {

    @Autowired
    private Converter<User, UserDto> userUserDtoConverter;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public WorkLog toModel(WorkLogDto dto) {
        if (dto == null)
            return null;
        else
            return new WorkLog(
                    dto.getId(),
                    dto.getTaskId(),
                    dto.getSpent(),
                    userUserDtoConverter.toModel(dto.getWorker()),
                    dto.getWhen(),
                    dto.getDescription(),
                    dto.getEstimateChange(),
                    dto.getEstimateValue()
            );
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public WorkLogDto toDto(WorkLog model) {
        if (model == null)
            return null;
        else
            return new WorkLogDto(
                    model.getId(),
                    model.getTaskId(),
                    model.getSpent(),
                    userUserDtoConverter.toDto(model.getWorker()),
                    model.getWhen(),
                    model.getDescription(),
                    model.getEstimateChange(),
                    model.getEstimateValue()
            );
    }
}
