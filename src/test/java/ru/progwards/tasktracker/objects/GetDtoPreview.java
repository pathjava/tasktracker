package ru.progwards.tasktracker.objects;

import ru.progwards.tasktracker.dto.*;

/**
 * Объекты DtoPreview для использования в методах тестирования
 *
 * @author Oleg Kiselev
 */
public class GetDtoPreview {

    public static RelatedTaskDtoPreview getRelatedTaskDtoPreview() {
        return new RelatedTaskDtoPreview(
                null,
                null,
                null,
                null
        );
    }

    public static RelationTypeDtoPreview getRelationTypeDtoPreview() {
        return new RelationTypeDtoPreview(
                null,
                null
        );
    }

    public static TaskDtoPreview getTaskDtoPreview() {
        return new TaskDtoPreview(
                null,
                null,
                null,
                null,
                null
        );
    }

    public static TaskTypeDtoPreview getTaskTypeDtoPreview() {
        return new TaskTypeDtoPreview(
                null,
                null
        );
    }

    public static WorkLogDtoPreview getWorkLogDtoPreview() {
        return new WorkLogDtoPreview(
                null,
                null,
                null,
                null
        );
    }

}
