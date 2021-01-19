package ru.progwards.tasktracker.controller.objects;

import ru.progwards.tasktracker.dto.RelationTypeDtoFull;
import ru.progwards.tasktracker.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.dto.WorkLogDtoFull;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Объекты Dto для использования в методах тестирования
 *
 * @author Oleg Kiselev
 */
public class GetDto {

    public static RelationTypeDtoFull getRelationTypeDto() {
        return new RelationTypeDtoFull(
                null,
                "relation name",
                null
        );
    }

    public static TaskTypeDtoFull getTaskTypeDto() {
        return new TaskTypeDtoFull(
                null,
                null,
                null,
                "type name"
        );
    }

    public static WorkLogDtoFull getWorkLogDto() {
        return new WorkLogDtoFull(
                null,
                null,
                Duration.ofHours(5),
                null,
                ZonedDateTime.now(),
                "Description workLog",
                "DONT_CHANGE",
                null
        );
    }

}
