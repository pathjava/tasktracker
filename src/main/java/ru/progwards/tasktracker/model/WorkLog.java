package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.progwards.tasktracker.model.types.EstimateChange;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * value object - объект бизнес логики (лог) (Журнала работ)
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "work_log")
public class WorkLog {

    @Id
    @SequenceGenerator(name = "WorkLogSeq", sequenceName = "work_log_seq", allocationSize = 1)
    @GeneratedValue(generator = "WorkLogSeq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    private Duration spent;

    @ManyToOne //TODO - fetch - ?
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User worker;

    @Column(nullable = false)
    private ZonedDateTime when;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstimateChange estimateChange;

    private Duration estimateValue;

}
