package ru.progwards.tasktracker.service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.progwards.tasktracker.util.types.EstimateChange;

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
@Entity
@Table(name = "work_log")
public class WorkLog {

    @Id
    @SequenceGenerator(name = "work_log_seq", sequenceName = "work_log_seq", allocationSize = 1)
    @GeneratedValue(generator = "work_log_seq", strategy = GenerationType.SEQUENCE)
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

    //TODO - enum - ?
    @Column(nullable = false)
    private EstimateChange estimateChange;
    private Duration estimateValue;

}
