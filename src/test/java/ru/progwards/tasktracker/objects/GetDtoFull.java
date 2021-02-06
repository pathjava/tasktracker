package ru.progwards.tasktracker.objects;

import ru.progwards.tasktracker.dto.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Random;

/**
 * Объекты DtoFull для использования в методах тестирования
 *
 * @author Oleg Kiselev
 */
public class GetDtoFull {

    /**
     * Метод генерации строки из случайных символов для использования в объектах,
     * где валидация не допускает использование не уникальных данных
     *
     * @return строка из случайных символов
     */
    public static String randomChar() {
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return result.toString();
    }

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
                "name " + randomChar()
        );
    }
    public static WorkFlowActionDtoFull getWorkFlowActionDtoFull() {
        return new WorkFlowActionDtoFull(
                null,
                null,
                "name " + randomChar(),
                null
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
