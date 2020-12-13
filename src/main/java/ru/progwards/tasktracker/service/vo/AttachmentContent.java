package ru.progwards.tasktracker.service.vo;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

/**
 * Файл-вложение, прикрепленный к задаче, или еще куда-нибудь в будущем
 *
 * Бизнес-объект предназначен для сохранения в репозиторий и для передачи пользователю
 * Для отображения в интерфейсе используйте TaskAttachment
 *
 * @author Gregory Lobkov
 */
@Entity
public class AttachmentContent {

    @Id
    @SequenceGenerator(name = "AttachmentContentSeq", sequenceName = "AttachmentContentSeq", allocationSize = 10, initialValue = 1)
    @GeneratedValue(generator = "AttachmentContentSeq", strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Содержимое вложения
     */
    @Lob
    private Blob data;

    /**
     * Обратная ссылка на связку вложения
     * В таблице колонки создаться не должно
     */
    @OneToMany(mappedBy = "content")
    List<TaskAttachment> taskAttachment;


    public AttachmentContent() {
    }

    public AttachmentContent(Long id, Blob data, List<TaskAttachment> taskAttachment) {
        this.id = id;
        this.data = data;
        this.taskAttachment = taskAttachment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getData() {
        return data;
    }

    public void setData(Blob data) {
        this.data = data;
    }

}
