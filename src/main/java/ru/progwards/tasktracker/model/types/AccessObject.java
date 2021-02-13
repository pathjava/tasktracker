package ru.progwards.tasktracker.model.types;

/**
К каким объектам разрешён доступ
 */
public enum AccessObject {
    PROJECT,
    TASK,
    ACCESS_RULE,
    RELATED_TASK,
    RELATION_TYPE,
    TASK_ATTACHMENT,
    TASK_ATTACHMENT_CONTENT,
    TASK_NOTE,
    TASK_PRIORITY,
    TASK_TYPE,
    USER,
    USER_ROLE,
    WORKFLOW,
    WORKFLOW_ACTION,
    WORKFLOW_STATUS,
    WORK_LOG
}
