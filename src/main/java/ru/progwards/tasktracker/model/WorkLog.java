package ru.progwards.tasktracker.model;

import lombok.*;
import ru.progwards.tasktracker.model.types.EstimateChange;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * value object - объект бизнес логики (лог) (Журнала работ)
 *
 * @author Oleg Kiselev
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "work_log")
public class WorkLog {

    @Id
    @SequenceGenerator(name = "WorkLogSeq", sequenceName = "work_log_seq", allocationSize = 1)
    @GeneratedValue(generator = "WorkLogSeq", strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private Task task;

    private Duration spent;

    @ManyToOne //TODO - fetch - ?
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private User worker;

    @Column(nullable = false)
    private ZonedDateTime when;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstimateChange estimateChange;

    private Duration estimateValue;

}
