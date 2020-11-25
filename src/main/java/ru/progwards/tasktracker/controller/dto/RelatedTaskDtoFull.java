package ru.progwards.tasktracker.controller.dto;

/**
 * Объект, содержащий данные о связанной задаче и выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class RelatedTaskDtoFull {

    private final Long id;
    private RelationTypeDtoFull relationType;
    private Long currentTaskId;
    private Long attachedTaskId;

    public RelatedTaskDtoFull(Long id, RelationTypeDtoFull relationType, Long currentTaskId, Long attachedTaskId) {
        this.id = id;
        this.relationType = relationType;
        this.currentTaskId = currentTaskId;
        this.attachedTaskId = attachedTaskId;
    }

    public Long getId() {
        return id;
    }

    public RelationTypeDtoFull getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationTypeDtoFull relationType) {
        this.relationType = relationType;
    }

    public Long getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(Long currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public Long getAttachedTaskId() {
        return attachedTaskId;
    }

    public void setAttachedTaskId(Long attachedTaskId) {
        this.attachedTaskId = attachedTaskId;
    }
}
