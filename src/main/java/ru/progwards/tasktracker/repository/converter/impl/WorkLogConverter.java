package ru.progwards.tasktracker.repository.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.repository.entity.UserEntity;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Конвертеры valueObject <-> entity (Журнала работ)
 *
 * @author Oleg Kiselev
 */
@Component
public class WorkLogConverter implements Converter<WorkLogEntity, WorkLog> {

    @Autowired
    private Converter<UserEntity, User> userConverter;
    @Autowired
    private Converter<TaskEntity, Task> taskConverter;

    /**
     * Метод конвертирует сущность Entity в бизнес объект
     *
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public WorkLog toVo(WorkLogEntity entity) {
        if (entity == null)
            return null;
        else
            return new WorkLog(
                    entity.getId(),
                    taskConverter.toVo(entity.getTask()),
                    checkDurationEntityNotNull(entity.getSpent()),
                    userConverter.toVo(entity.getWorker()),
                    checkUpdatedEntityNotNull(entity.getWhen()),
                    entity.getDescription(),
                    null,
                    null
            );
    }

    /**
     * Метод проверки поля и возвращения содержимого
     * из условия что было на входе, null или объект
     *
     * @param duration секунды, могут быть пустыми и значение
     * @return продолжительность или пусто
     */
    public Duration checkDurationEntityNotNull(Long duration) {
        return duration != null ? Duration.ofSeconds(duration) : null;
    }

    /**
     * Метод проверки поля и возвращения содержимого
     * из условия что было на входе, null или объект
     *
     * @param updated секунды, могут быть пустыми и значение
     * @return дату-время или пусто
     */
    public ZonedDateTime checkUpdatedEntityNotNull(Long updated) {
        return updated != null ? ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(updated), ZoneId.of("Europe/Moscow")) : null;
    }

    /**
     * Метод конвертирует бизнес объект в сущность Entity
     *
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public WorkLogEntity toEntity(WorkLog valueObject) {
        if (valueObject == null)
            return null;
        else
            return new WorkLogEntity(
                    valueObject.getId(),
                    taskConverter.toEntity(valueObject.getTask()),
                    checkDurationValueObjectNotNull(valueObject.getSpent()),
                    userConverter.toEntity(valueObject.getWorker()),
                    checkZonedDateTimeValueObjectNotNull(valueObject.getWhen()),
                    valueObject.getDescription()
            );
    }

    /**
     * Метод проверки поля и возвращения содержимого
     * из условия что было на входе, null или объект
     *
     * @param duration продолжительность
     * @return продолжительность в секундах или пусто
     */
    public Long checkDurationValueObjectNotNull(Duration duration) {
        return duration != null ? duration.toSeconds() : null;
    }

    /**
     * Метод проверки поля и возвращения содержимого
     * из условия что было на входе, null или объект
     *
     * @param time дата-время
     * @return дата-время в секундах или пусто
     */
    public Long checkZonedDateTimeValueObjectNotNull(ZonedDateTime time) {
        return time != null ? time.toEpochSecond() : null;
    }
}
