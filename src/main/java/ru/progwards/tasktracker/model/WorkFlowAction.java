package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

/**
 * Действия над задачей, переводящие её из одного состояния WorkFlowStatus в другое
 *
 * @author Gregory Lobkov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workflow_action")
public class WorkFlowAction {

    @Id
    @SequenceGenerator(name = "workflow_action_seq", sequenceName = "workflow_action_seq", allocationSize = 1)
    @GeneratedValue(generator = "workflow_action_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    /**
     * Родительский статус
     *
     * Статус задач, к которым применимо данное действие
     */
    @Lazy
    @ManyToOne
    @JoinColumn(name = "parent_status_id", referencedColumnName = "id")
    WorkFlowStatus parentStatus; //deprecated

    /**
     * Наименование действия
     */
    @Column(name = "name", nullable = false)
    String name;

    /**
     * Статус, в который переводится задача после применения действия
     */
    @Lazy
    @ManyToOne
    @JoinColumn(name = "next_status_id", referencedColumnName = "id")
    WorkFlowStatus nextStatus;

}
