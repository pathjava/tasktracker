package ru.progwards.tasktracker.service.vo;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Связка между задачами и вложениями
 *
 * @author Gregory Lobkov
 */
@Entity
public class TaskAttachment {

    /**
     * ID связки задача-вложение
     */
    @Id
    @SequenceGenerator(name = "TaskAttachmentSeq", sequenceName = "TaskAttachmentSeq", allocationSize = 10, initialValue = 1)
    @GeneratedValue(generator = "TaskAttachmentSeq", strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Ссылка на задачу
     */
    //@OneToOne(fetch = FetchType.LAZY, mappedBy = "attachments")
    private Long task;

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
     * Вложение
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private AttachmentContent content;

    public TaskAttachment() {
    }

    public TaskAttachment(Long id, Long task, String name, String extension, Long size, ZonedDateTime created, AttachmentContent content) {
        this.id = id;
        this.task = task;
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.created = created;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTask() {
        return task;
    }

    public void setTask(Long task) {
        this.task = task;
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
