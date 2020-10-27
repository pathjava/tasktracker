package ru.progwards.tasktracker.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

/**
 * Связка между задачами и вложениями
 * много ко многим
 *
 * @author Gregory Lobkov
 */
public class TaskAttachmentDto {

    /**
     * ID связки задача-вложение
     */
    private Long id;

    /**
     * Ссылка на задачу
     */
    private Long taskId;

    /**
     * Ссылка на вложение
     */
    private Long attachmentContentId;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime dateCreated;


    public TaskAttachmentDto(Long id, Long taskId, Long attachmentContentId, String name, String extension, Long size, ZonedDateTime dateCreated) {
        this.id = id;
        this.taskId = taskId;
        this.attachmentContentId = attachmentContentId;
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

    public Long getAttachmentContentId() {
        return attachmentContentId;
    }

    public void setAttachmentContentId(Long attachmentContentId) {
        this.attachmentContentId = attachmentContentId;
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
