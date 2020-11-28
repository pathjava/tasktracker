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
    private ZonedDateTime created;

    /**
     * Ссылка на вложение
     */
    private Long contentId;

    /**
     * Вложение
     */
    private AttachmentContent content;


    public TaskAttachment(Long id, Long taskId, String name, String extension, Long size, ZonedDateTime created, Long contentId, AttachmentContent content) {
        this.id = id;
        this.taskId = taskId;
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.created = created;
        this.contentId = contentId;
        this.content = content;
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

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
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

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

}
