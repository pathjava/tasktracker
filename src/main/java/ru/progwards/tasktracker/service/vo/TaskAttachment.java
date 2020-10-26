package ru.progwards.tasktracker.service.vo;

import java.time.ZonedDateTime;

/**
 * Связка между задачами и вложениями
 * много ко многим
 *
 * @author Gregory Lobkov
 */
public class TaskAttachment {

    /**
     * ID связки задача-вложение
     */
    private Long id;

    /**
     * Ссылка на задачу
     */
    private Long taskId;

    /**
     * Задача
     */
    private Task task;

    /**
     * Ссылка на вложение
     */
    private Long attachmentContentId;

    /**
     * Вложение
     */
    private AttachmentContent content;

    /**
     * Полное имя файла-вложения
     */
    private String name;

    /**
     * Расширение файла
     */
    private String extension;

    /**
     * Размер в байтах
     */
    private Long size;

    /**
     * Дата создания
     */
    private ZonedDateTime dateCreated;


    public TaskAttachment(Long id, Long taskId, Task task, Long attachmentContentId, AttachmentContent content, String name, String extension, Long size, ZonedDateTime dateCreated) {
        this.id = id;
        this.taskId = taskId;
        this.task = task;
        this.attachmentContentId = attachmentContentId;
        this.content = content;
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getAttachmentContentId() {
        return attachmentContentId;
    }

    public void setAttachmentContentId(Long attachmentContentId) {
        this.attachmentContentId = attachmentContentId;
    }

    public AttachmentContent getContent() {
        return content;
    }

    public void setContent(AttachmentContent content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

}
