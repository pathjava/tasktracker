package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

/**
 * Файл-вложение, прикрепленный к задаче, или еще куда-нибудь в будущем
 *
 * Бизнес-объект предназначен для сохранения в репозиторий и для передачи пользователю
 * Для отображения в интерфейсе используйте TaskAttachment
 *
 * @author Gregory Lobkov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(of = {"id"})
@Table(name = "task_attachment_content")
public class TaskAttachmentContent {

    @Id
    @SequenceGenerator(name = "task_attachment_content_seq", sequenceName = "task_attachment_content_seq", allocationSize = 10, initialValue = 1)
    @GeneratedValue(generator = "task_attachment_content_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /**
     * Содержимое вложения
     */
    @Lob
    private byte[] data;

    /**
     * Обратная ссылка на связку вложения
     * В таблице колонки создаться не должно
     */
    @Lazy
    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    List<TaskAttachment> taskAttachment;

}
