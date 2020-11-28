package ru.progwards.tasktracker.controller.dto;

/**
 * Объект, содержащий краткие данные о связанной задаче и выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class RelatedTaskDtoPreview {

    private final Long id;
    private RelationTypeDtoPreview relationType;
    private Long currentTaskId;
    private TaskDtoPreview attachedTask;

    public RelatedTaskDtoPreview(
            Long id, RelationTypeDtoPreview relationType, Long currentTaskId, TaskDtoPreview attachedTask
    ) {
        this.id = id;
        this.relationType = relationType;
        this.currentTaskId = currentTaskId;
        this.attachedTask = attachedTask;
    }

    public Long getId() {
        return id;
    }

    public RelationTypeDtoPreview getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationTypeDtoPreview relationType) {
        this.relationType = relationType;
    }

    public Long getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(Long currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public TaskDtoPreview getAttachedTask() {
        return attachedTask;
    }

    public void setAttachedTask(TaskDtoPreview attachedTask) {
        this.attachedTask = attachedTask;
    }
}
