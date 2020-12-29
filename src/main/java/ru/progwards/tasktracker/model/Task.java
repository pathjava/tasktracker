package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * value object - объект бизнес логики (задача)
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "task")
public class Task {

    @Id
    @SequenceGenerator(name = "TaskSeq", sequenceName = "task_seq", allocationSize = 1)
    @GeneratedValue(generator = "TaskSeq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String code;

    @Column(nullable = false)
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
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
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

    private Duration estimation;
//    @Column(name = "time_spent")
    private Duration timeSpent;
//    @Column(name = "time_left")
    private Duration timeLeft;

    @OneToMany(mappedBy = "currentTask", fetch = FetchType.LAZY)
    @Where(clause = "deleted = false")
    private List<RelatedTask> relatedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "attachedTask", fetch = FetchType.LAZY)
    private List<RelatedTask> relatedTasksAttached = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskAttachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<WorkLog> workLogs = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskNote> notes = new ArrayList<>();

//    @Type(type = "true_false")
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

}
