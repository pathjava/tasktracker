package ru.progwards.tasktracker.service.vo;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * TaskPriority - бизнес-модель
 * @author Pavel Khovaylo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "task_priority")
public class TaskPriority {
    /**
     * идентификатор
     */
    @Id
    @SequenceGenerator(name = "TASK_PRIORITY_SEQ", sequenceName = "task_priority_seq", allocationSize = 1)
    @GeneratedValue(generator = "TASK_PRIORITY_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * имя
     */
    @Basic
    @Column(name = "name", nullable = false, length = 40)
    private String name;
    /**
     * числовой приоритет
     */
    @Basic
    @Column(name = "value", nullable = false)
    private Integer value;
    /**
     * список задач с данным приоритетом
     */
    @OneToMany(mappedBy = "priority", fetch = FetchType.LAZY)
    private List<Task> tasks;
}