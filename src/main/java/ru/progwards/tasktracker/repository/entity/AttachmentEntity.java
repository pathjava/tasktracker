package ru.progwards.tasktracker.repository.entity;

public class AttachmentEntity {


    private long id;

    /**
     * Имя для вложения
     */
    private String name;

    /**
     * Содержимое вложения
     */
    private byte[] rawData;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getRawData() {
        return rawData;
    }

    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }
}
