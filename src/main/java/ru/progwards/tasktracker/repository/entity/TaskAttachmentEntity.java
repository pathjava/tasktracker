package ru.progwards.tasktracker.repository.entity;

public class TaskAttachmentEntity {


    private long id;

    /**
     * Ссылка на задачу
     */
    private long taskId;

    /**
     * Ссылка на вложение
     */
    private long attachmentId;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(long attachmentId) {
        this.attachmentId = attachmentId;
    }
}
