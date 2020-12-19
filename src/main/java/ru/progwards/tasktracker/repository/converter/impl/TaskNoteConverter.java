package ru.progwards.tasktracker.repository.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.TaskNoteEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.vo.TaskNote;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * конвертер valueObject <-> entity
 *
 * @author Konstantin Kishkin
 */
@Component
public class TaskNoteConverter implements Converter<TaskNoteEntity, TaskNote> {

    /**
     * @param entity сущность, полученная из БД
     * @return value object - объект бизнес логики
     */
    @Override
    public TaskNote toVo(TaskNoteEntity entity) {
//        if (entity == null)
            return null;
//        else
//            return new TaskNote(
//                    entity.getId(),
//                    entity.getTask_id(),
//                    entity.getAuthor(),
//                    entity.getComment()
//            );
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
     * @param valueObject value object - объект бизнес логики
     * @return сущность для БД
     */
    @Override
    public TaskNoteEntity toEntity(TaskNote valueObject) {
//        if (valueObject == null)
            return null;
//        else
//            return new TaskNoteEntity(
//                    valueObject.getId(),
//                    valueObject.getTask(),
//                    valueObject.getAuthor(),
//                    valueObject.getUpdater(),
//                    checkZonedDateTimeValueObjectNotNull(valueObject.getCreated()),
//                    checkZonedDateTimeValueObjectNotNull(valueObject.getUpdated()),
//                    valueObject.getComment()
//            );
    }

    /**
     * @param time дата-время
     * @return дата-время в секундах или пусто
     */
    private Long checkZonedDateTimeValueObjectNotNull(ZonedDateTime time) {
        return time != null ? time.toEpochSecond() : null;
    }
}

