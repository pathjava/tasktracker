package ru.progwards.tasktracker.controller.dto;

/**
 * Объект, содержащий данные о связанной задаче и выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class RelatedTaskDto {

    private final Long id;
    private RelationTypeDto relationType;
    private Long currentTaskId;
    private Long attachedTaskId;

    public RelatedTaskDto(Long id, RelationTypeDto relationType, Long currentTaskId, Long attachedTaskId) {
        this.id = id;
        this.relationType = relationType;
        this.currentTaskId = currentTaskId;
        this.attachedTaskId = attachedTaskId;
    }

    public Long getId() {
        return id;
    }

    public RelationTypeDto getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationTypeDto relationType) {
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
