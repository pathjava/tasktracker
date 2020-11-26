package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserDtoPreview;
import ru.progwards.tasktracker.controller.dto.WorkLogDtoFull;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkLog;
import ru.progwards.tasktracker.util.types.EstimateChange;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
public class WorkLogDtoFullConverter implements Converter<WorkLog, WorkLogDtoFull> {

    @Autowired
    private Converter<User, UserDtoPreview> userDtoConverter;

    /**
     * Метод конвертирует Dto сущность в бизнес объект
     *
     * @param dto сущность, приходящая из пользовательского интерфейса
     * @return value object - объект бизнес логики
     */
    @Override
    public WorkLog toModel(WorkLogDtoFull dto) {
        if (dto == null)
            return null;
        else
            return new WorkLog(
                    dto.getId(),
                    dto.getTaskId(),
                    dto.getSpent(),
                    userDtoConverter.toModel(dto.getWorker()),
                    dto.getWhen(),
                    dto.getDescription(),
                    stringToEnum(dto.getEstimateChange()),
                    dto.getEstimateValue()
            );
    }

    /**
     * Метод конвертации строкового значения в ENUM
     *
     * @param estimateChange строковое значение
     * @return перечисление enum
     */
    private EstimateChange stringToEnum(String estimateChange) {
        try {
            return EstimateChange.valueOf(estimateChange.toUpperCase());
        } catch (BadRequestException e) {
            throw new BadRequestException(
                    estimateChange + " не соответствует ни одному перечислению EstimateChange!");
        }
    }

    /**
     * Метод конвертирует бизнес объект в сущность Dto
     *
     * @param model value object - объект бизнес логики
     * @return сущность, возвращаемая в пользовательский интерфейс
     */
    @Override
    public WorkLogDtoFull toDto(WorkLog model) {
        if (model == null)
            return null;
        else
            return new WorkLogDtoFull(
                    model.getId(),
                    model.getTaskId(),
                    model.getSpent(),
                    userDtoConverter.toDto(model.getWorker()),
                    model.getWhen(),
                    model.getDescription(),
                    enumToString(model.getEstimateChange()),
                    model.getEstimateValue()
            );
    }

    /**
     * Метод конвертации ENUM в строку
     *
     * @param estimateChange enum перечисление
     * @return строковое значение
     */
    private String enumToString(EstimateChange estimateChange) {
        return estimateChange == null ? null : estimateChange.toString();
    }
}
