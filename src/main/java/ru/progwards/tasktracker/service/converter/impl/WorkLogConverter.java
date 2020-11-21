package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.converter.Converter;
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
                    entity.getTaskId(),
                    checkDurationEntityNotNull(entity.getSpent()),
                    entity.getWorker(),
                    checkUpdatedEntityNotNull(entity.getWhen()),
                    entity.getDescription(),
                    null,
                    null
            );
    }

    /**
     * @param duration секунды, могут быть пустыми и значение
     * @return продолжительность или пусто
     */
    private Duration checkDurationEntityNotNull(Long duration) {
        return duration != null ? Duration.ofSeconds(duration) : null;
    }

    /**
     * @param updated секунды, могут быть пустыми и значение
     * @return дату-время или пусто
     */
    private ZonedDateTime checkUpdatedEntityNotNull(Long updated) {
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
                    valueObject.getTaskId(),
                    checkDurationValueObjectNotNull(valueObject.getSpent()),
                    valueObject.getWorker(),
                    checkZonedDateTimeValueObjectNotNull(valueObject.getWhen()),
                    valueObject.getDescription()
            );
    }

    /**
     * @param duration продолжительность
     * @return продолжительность в секундах или пусто
     */
    private Long checkDurationValueObjectNotNull(Duration duration) {
        return duration != null ? duration.toSeconds() : null;
    }

    /**
     * @param time дата-время
     * @return дата-время в секундах или пусто
     */
    private Long checkZonedDateTimeValueObjectNotNull(ZonedDateTime time) {
        return time != null ? time.toEpochSecond() : null;
    }
}
