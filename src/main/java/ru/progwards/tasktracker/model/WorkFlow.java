package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

/**
 * Бизнес-процесс
 * Формирует дерево движения задачи по статусам
 *
 * @author Gregory Lobkov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workflow")
public class WorkFlow {

    @Id
    @SequenceGenerator(name = "workflow_seq", sequenceName = "workflow_seq", allocationSize = 1)
    @GeneratedValue(generator = "workflow_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    /**
     * Наименование
     */
    @Column(name = "name", nullable = false)
    String name;

    /**
     * Признак шаблона
     *
     * Чтобы легко можно было отличить воркфлоу конкретного процесса
     * от шаблона, на основе которого будет все создаваться,
     * иначе его нельзя будет настраивать индивидуально
     *
     * Нельзя делать примитив, т.к. в фасаде CopyService невозможно будет
     * решить, копировать ли данное свойство из template, или нет
     */
    @Column(name = "pattern", nullable = false)
    Boolean pattern;

    /**
     * С какого статуса начинать движение задачи
     */
    @Lazy
    @ManyToOne
    @JoinColumn(name = "start_status_id", referencedColumnName = "id")
    WorkFlowStatus startStatus;

    /**
     * Статусы, возможные по бизнес-процессу
     */
    @Lazy
    @OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY)
    List<WorkFlowStatus> statuses;

    @Lazy
    @OneToMany(mappedBy = "workFlow", fetch = FetchType.LAZY)
    List<TaskType> taskTypes;

}
