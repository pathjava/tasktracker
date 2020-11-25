package ru.progwards.tasktracker.controller.dto;

/**
 * @author Oleg Kiselev
 */
public class RelatedTaskDtoPreview {

    private final Long id;
    private RelationTypeDtoPreview relationType;
    private Long currentTaskId;
    private Long attachedTaskId;

    public RelatedTaskDtoPreview(Long id, RelationTypeDtoPreview relationType, Long currentTaskId, Long attachedTaskId) {
        this.id = id;
        this.relationType = relationType;
        this.currentTaskId = currentTaskId;
        this.attachedTaskId = attachedTaskId;
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

    public Long getAttachedTaskId() {
        return attachedTaskId;
    }

    public void setAttachedTaskId(Long attachedTaskId) {
        this.attachedTaskId = attachedTaskId;
    }
}
