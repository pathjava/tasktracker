package service.vo;

import util.types.Priority;
import util.types.TaskType;
import util.types.WorkflowStatus;

public class TaskModel {

    private final Long id;
    private final String name;
    private final String description;
    private final TaskType type;
    private final Priority priority;
    private final Long authorUserId;
    private final Long executorUserId;
    private final Long created;
    private final Long updated;
    private final int storyPoint;
    private final Long projectId;
    private final String strCode; /* изменил имя на верблюжью нотацию с strcode на strCode*/
    private final WorkflowStatus wfStatus;
    private final String version;
    private final Long planDuration;
    private final Long spentDuration;
    private final Long leftDuration;


    public TaskModel(Long id, String name, String description,
                     TaskType type, Priority priority,
                     Long authorUserId, Long executorUserId,
                     Long created, Long updated, int storyPoint,
                     Long projectId, String strCode, WorkflowStatus wfStatus,
                     String version, Long planDuration, Long spentDuration, Long leftDuration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.authorUserId = authorUserId;
        this.executorUserId = executorUserId;
        this.created = created;
        this.updated = updated;
        this.storyPoint = storyPoint;
        this.projectId = projectId;
        this.strCode = strCode;
        this.wfStatus = wfStatus;
        this.version = version;
        this.planDuration = planDuration;
        this.spentDuration = spentDuration;
        this.leftDuration = leftDuration;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskType getType() {
        return type;
    }

    public Priority getPriority() {
        return priority;
    }

    public Long getAuthorUserId() {
        return authorUserId;
    }

    public Long getExecutorUserId() {
        return executorUserId;
    }

    public Long getCreated() {
        return created;
    }

    public Long getUpdated() {
        return updated;
    }

    public int getStoryPoint() {
        return storyPoint;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getStrCode() {
        return strCode;
    }

    public WorkflowStatus getWfStatus() {
        return wfStatus;
    }

    public String getVersion() {
        return version;
    }

    public Long getPlanDuration() {
        return planDuration;
    }

    public Long getSpentDuration() {
        return spentDuration;
    }

    public Long getLeftDuration() {
        return leftDuration;
    }
}
