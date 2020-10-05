package ru.progwards.tasktracker.service.vo;

public class Attachment {


    private long id;

    /**
     * Полное имя файла-вложения
     */
    private String fileName;

    /**
     * Имя файла
     */
    private String name;

    /**
     * Расширение файла
     */
    private String extension;

    /**
     * Содержимое вложения
     */
    private byte[] rawData;

}
