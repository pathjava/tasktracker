package ru.progwards.tasktracker.repository.deprecated.entity;

/**
 * Вложение задачи
 * 
 * Содержимое лежит отдельно, в AttachmentContent
 *
 * @author Gregory Lobkov
 */
public class TaskAttachmentEntity {


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
     * Дата создания, секунд от 1970-01-01T00:00:00Z
     */
    private Long created;

    /**
     * Ссылка на содержимое вложения
     */
    private Long contentId;


    /**
     * Пустой конструктор - из за особенности работы имплементации репозитория
     */
    public TaskAttachmentEntity() {
    }

    public TaskAttachmentEntity(Long id, Long taskId, String name, String extension, Long size, Long created, Long contentId) {
        this.id = id;
        this.taskId = taskId;
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.created = created;
        this.contentId = contentId;
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

}
