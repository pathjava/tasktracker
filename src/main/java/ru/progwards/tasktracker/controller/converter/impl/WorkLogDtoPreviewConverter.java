package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserDtoPreview;
import ru.progwards.tasktracker.controller.dto.WorkLogDtoPreview;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkLog;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class WorkLogDtoPreviewConverter implements Converter<WorkLog, WorkLogDtoPreview> {

    @Autowired
    private GetService<Long, WorkLog> workLogGetService;
    @Autowired
    private Converter<User, UserDtoPreview> userUserDtoConverter;

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
                    workLog.getTaskId(),
                    dto.getSpent(),
                    userUserDtoConverter.toModel(dto.getWorker()),
                    dto.getWhen(),
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
                    model.getWhen()
            );
    }
}
