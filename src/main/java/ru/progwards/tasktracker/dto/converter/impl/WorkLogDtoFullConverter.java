package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.WorkLogDtoFull;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.model.WorkLog;
import ru.progwards.tasktracker.model.types.EstimateChange;
import ru.progwards.tasktracker.service.GetService;

/**
 * Конвертеры valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkLogDtoFullConverter implements Converter<WorkLog, WorkLogDtoFull> {

    private final @NonNull Converter<User, UserDtoPreview> userDtoConverter;
    private final @NonNull GetService<Long, Task> getService;

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
        else {
            Task task = getService.get(dto.getTaskId());
            return new WorkLog(
                    dto.getId(),
                    task,
                    dto.getSpent(),
                    userDtoConverter.toModel(dto.getWorker()),
                    dto.getWhen(),
                    dto.getDescription(),
                    stringToEnum(dto.getEstimateChange()),
                    dto.getEstimateValue()
            );
        }
    }

    /**
     * Метод конвертации строкового значения в ENUM
     *
     * @param estimateChange строковое значение
     * @return перечисление enum
     */
    private EstimateChange stringToEnum(String estimateChange) {
        if (estimateChange != null)
            for (EstimateChange value : EstimateChange.values()) {
                if (value.name().equalsIgnoreCase(estimateChange))
                    return value;
            }

        throw new BadRequestException(
                estimateChange + " не соответствует ни одному перечислению EstimateChange!"
        );
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
                    model.getTask().getId(),
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
