package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Связка между задачами и вложениями
 *
 * @author Gregory Lobkov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_attachment")
public class TaskAttachment {

    /**
     * ID связки задача-вложение
     */
    @Id
    @SequenceGenerator(name = "task_attachment_seq", sequenceName = "task_attachment_seq", allocationSize = 10, initialValue = 1)
    @GeneratedValue(generator = "task_attachment_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /**
     * Ссылка на задачу
     */
    @Lazy
    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    /**
     * Полное имя файла-вложения
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Расширение файла
     */
    @Column(name = "extension")
    private String extension;

    /**
     * Размер в байтах
     */
    @Column(name = "size")
    private Long size;

    /**
     * Дата создания
     */
    @Column(name = "created")
    private ZonedDateTime created;

    /**
     * Вложение
     */
    @Lazy
    @ManyToOne
    @JoinColumn(name = "content_id", referencedColumnName = "id")
    private TaskAttachmentContent content;

}
