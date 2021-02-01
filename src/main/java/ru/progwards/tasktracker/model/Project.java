package ru.progwards.tasktracker.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Project - бизнес-модель проекта
 * @author Pavel Khovaylo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString(of = {"id"})
@Table(name = "project")
//В SQL-запрос попадают только измененные поля
@DynamicUpdate
public class Project  {
    /**
     * идентификатор проекта
     */
    @Id
    @SequenceGenerator(name = "PROJECT_SEQ", sequenceName = "project_seq", allocationSize = 1)
    @GeneratedValue(generator = "PROJECT_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    Long id;
    /**
     * имя проекта
     */
    @NotEmpty
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    String name;
    /**
     * описание проекта
     */
    @Basic
    @Column(name = "description", length = 800)
    String description;
    /**
     * уникальная аббревиатура, созданная на основании имени проекта
     */
    @NotEmpty
    @Basic
    @Column(name = "prefix", unique = true, length = 10)
    String prefix;
    /**
     * владелец (создатель) проекта
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    User owner;
    /**
     * время создания проекта
     */
    @NotNull
    @Column(name = "created")
    ZonedDateTime created;
    /**
     * список задач проекта
     * в списке содержаться объекты Task, у которых свойство deleted = false
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @Where(clause = "deleted = false")
    List<Task> tasks;
    /**
     * список типов задач, относящихся к данному проекту
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    List<TaskType> taskTypes;
    /**
     * хранит код последней добавленной задачи к данному проекту
     */
    @NotNull
    @Basic
    @Column(name = "last_task_code")
    Long lastTaskCode;
    /**
     * информация об удалении проекта
     */
    @NotNull
    @Basic
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    boolean deleted;
}