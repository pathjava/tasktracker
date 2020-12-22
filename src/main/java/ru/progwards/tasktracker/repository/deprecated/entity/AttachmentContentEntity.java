package ru.progwards.tasktracker.repository.deprecated.entity;

/**
 * Вложение - файл
 *
 * @author Gregory Lobkov
 */
public class AttachmentContentEntity {


    private Long id;

    /**
     * Содержимое вложения
     */
    private byte[] data;


    public AttachmentContentEntity() {
    }

    public AttachmentContentEntity(Long id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
