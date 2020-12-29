package ru.progwards.tasktracker.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    Long id;
    /**
     * имя
     */
    @Basic
    @Column(name = "name", nullable = false, length = 40)
    String name;
    /**
     * числовой приоритет
     */
    @Basic
    @Column(name = "value", nullable = false)
    Integer value;
    /**
     * список задач с данным приоритетом
     */
    @OneToMany(mappedBy = "priority", fetch = FetchType.LAZY)
    List<Task> tasks;
}