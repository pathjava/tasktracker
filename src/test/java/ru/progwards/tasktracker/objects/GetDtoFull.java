package ru.progwards.tasktracker.objects;

import ru.progwards.tasktracker.dto.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;

/**
 * Объекты DtoFull для использования в методах тестирования
 *
 * @author Oleg Kiselev
 */
public class GetDtoFull {

    public static RelatedTaskDtoFull getRelatedTaskDtoFull() {
        return new RelatedTaskDtoFull(
                null,
                null,
                null,
                null
        );
    }

    public static RelationTypeDtoFull getRelationTypeDtoFull() {
        return new RelationTypeDtoFull(
                null,
                "relation name",
                null
        );
    }

    public static TaskDtoFull getTaskDtoFull() {
        return new TaskDtoFull(
                null,
                null,
                "Test task",
                "Description task",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                Collections.emptyList(),
                null,
                null,
                null,
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public static TaskTypeDtoFull getTaskTypeDtoFull() {
        return new TaskTypeDtoFull(
                null,
                null,
                null,
                "type name"
        );
    }

    public static WorkLogDtoFull getWorkLogDtoFull() {
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
