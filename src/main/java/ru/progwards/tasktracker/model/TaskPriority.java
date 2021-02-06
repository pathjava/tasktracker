package ru.progwards.tasktracker.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TaskPriority - бизнес-модель
 * @author Pavel Khovaylo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString(of = {"id"})
@Table(name = "task_priority")
//В SQL-запрос попадают только измененные поля
@DynamicUpdate
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
    @NotEmpty
    @Basic
    @Column(name = "name", nullable = false, length = 40)
    String name;
    /**
     * числовой приоритет
     */
    @NotNull
    @Min(0)
    @Max(Integer.MAX_VALUE)
    @Basic
    @Column(name = "value", nullable = false)
    Integer value;
    /**
     * список задач с данным приоритетом
     * в списке содержаться объекты Task, у которых свойство deleted = false
     */
    @OneToMany(mappedBy = "priority", fetch = FetchType.LAZY)
    @Where(clause = "deleted = false")
    List<Task> tasks;
}