package ru.progwards.tasktracker.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * value object - это объект бизнес логики (комментарий)
 *
 * @author Konstantin Kishkin
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="task_note")
public class TaskNote {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name="task_id")
    private Task task;

    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name="author_id")
    private User author;

    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name="updater_id")
    private User updater;

    private String comment;
    private ZonedDateTime created;
    private ZonedDateTime updated;

}
