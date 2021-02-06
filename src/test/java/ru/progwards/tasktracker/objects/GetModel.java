package ru.progwards.tasktracker.objects;

import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.model.types.AccessObject;
import ru.progwards.tasktracker.model.types.AccessType;
import ru.progwards.tasktracker.model.types.EstimateChange;
import ru.progwards.tasktracker.model.types.SystemRole;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Random;

/**
 * Объекты Model для использования в методах тестирования
 *
 * @author Oleg Kiselev
 */
public class GetModel {

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

    public static AccessRule getAccessRule() {
        return new AccessRule(
                null,
                null,
                AccessObject.ACCESS_RULE,
                null,
                null,
                AccessType.MODIFY
        );
    }

    public static Project getProjectModel() {
        return new Project(
                null,
                "Test project " + randomChar(),
                "Description Test project",
                randomChar(),
                null,
                ZonedDateTime.now(),
                Collections.emptyList(),
                Collections.emptyList(),
                0L,
                false
        );
    }

    public static RelatedTask getRelatedTaskModel() {
        return new RelatedTask(
                null,
                null,
                null,
                null,
                false
        );
    }

    public static RelationType getRelationTypeModel() {
        return new RelationType(
                null,
                "relation name",
                null,
                Collections.emptyList()
        );
    }

    public static Task getTaskModel() {
        return new Task(
                null,
                randomChar(),
                "Test task",
                "Description task",
                null,
                null,
                null,
                null,
                null,
                ZonedDateTime.now(),
                null,
                null,
                null,
                null,
                null,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                false
        );
    }

    public static TaskNote getTaskNote() {
        return new TaskNote(
                null,
                null,
                null,
                null,
                "Comment",
                ZonedDateTime.now(),
                ZonedDateTime.now()
        );
    }

    public static TaskType getTaskTypeModel() {
        return new TaskType(
                null,
                null,
                null,
                "name " + randomChar(),
                Collections.emptyList()
        );
    }

    public static WorkFlowAction getWorkFlowAction() {
        return new WorkFlowAction(
                null,
                null,
                "name " + randomChar(),
                null
        );
    }

    public static User getUserModel() {
        return new User(
                null,
                "Ivan",
                "mail@mail" + randomChar() + ".ru",
                "YqIe3i0S4co3XO8tW6bQ",
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public static UserRole getUserRole() {
        return new UserRole(
                null,
                "Администраторы",
                SystemRole.ADMIN,
                null,
                null
        );
    }

    public static WorkFlow getWorkFlowModel(){
        return new WorkFlow(
                null,
                null,
                false,
                null,
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public static WorkLog getWorkLogModel() {
        return new WorkLog(
                null,
                null,
                Duration.ofHours(5),
                null,
                ZonedDateTime.now(),
                "Description workLog",
                EstimateChange.DONT_CHANGE,
                null
        );
    }

}
