package ru.progwards.tasktracker.service.vo;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * value object - объект бизнес логики (задача)
 *
 * @author Oleg Kiselev
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "task")
public class Task {

    @Id
    @SequenceGenerator(name = "TaskSeq", sequenceName = "task_seq", allocationSize = 1)
    @GeneratedValue(generator = "TaskSeq", strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String code;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TaskType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private TaskPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @EqualsAndHashCode.Include
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private User executor;

    @Column(nullable = false)
    private ZonedDateTime created;
    private ZonedDateTime updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private WorkFlowStatus status;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<WorkFlowAction> actions;

    private Duration estimation;
    private Duration timeSpent;
    private Duration timeLeft;

    @OneToMany(mappedBy = "currentTask", fetch = FetchType.LAZY)
    private List<RelatedTask> relatedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "attachedTask", fetch = FetchType.LAZY)
    private List<RelatedTask> relatedTasksAttached = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskAttachment> attachments;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<WorkLog> workLogs;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskNote> notes;

    /*boolean isDeleted;*/ //TODO

}
