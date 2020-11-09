package ru.progwards.tasktracker.controller.dto;

import ru.progwards.tasktracker.service.vo.RelationType;

/**
 * сущность, содержащая данные о связанной задаче и выводимые в пользовательском интерфейсе
 *
 * @author Oleg Kiselev
 */
public class RelatedTaskDto {

    private final Long id;
    private RelationType relationType;
    private Long parentTaskId;
    private Long taskId;

    public RelatedTaskDto(Long id, RelationType relationType, Long parentTaskId, Long taskId) {
        this.id = id;
        this.relationType = relationType;
        this.parentTaskId = parentTaskId;
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public Long getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
