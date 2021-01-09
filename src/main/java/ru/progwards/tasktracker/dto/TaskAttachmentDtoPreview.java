package ru.progwards.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class TaskAttachmentDtoPreview {

    /**
     * ID связки задача-вложение
     */
    @NotNull
    private Long id;

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
    private ZonedDateTime created;

}