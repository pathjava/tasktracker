package ru.progwards.tasktracker.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Project - бизнес-модель проекта
 * @author Pavel Khovaylo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "project")
public class Project  {
    /**
     * идентификатор проекта
     */
    @Id
    @SequenceGenerator(name = "PROJECT_SEQ", sequenceName = "project_seq", allocationSize = 1)
    @GeneratedValue(generator = "PROJECT_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * имя проекта
     */
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    /**
     * описание проекта
     */
    @Basic
    @Column(name = "description", nullable = true, length = 800)
    private String description;
    /**
     * уникальная аббревиатура, созданная на основании имени проекта
     */
    @Basic
    @Column(name = "prefix", nullable = false, length = 10)
    private String prefix;
    /**
     * владелец (создатель) проекта
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;
    /**
     * время создания проекта
     */
    @Column(name = "created")
    private ZonedDateTime created;
    /**
     * список задач проекта
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Task> tasks;
    /**
     * список типов задач, относящихся к данному проекту
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<TaskType> taskTypes;
    /**
     * хранит код последней добавленной задачи к данному проекту
     */
    @Basic
    @Column(name = "last_task_code", nullable = false)
    private Long lastTaskCode;
    /**
     * информация об удалении проекта
     */
    @Basic
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
}