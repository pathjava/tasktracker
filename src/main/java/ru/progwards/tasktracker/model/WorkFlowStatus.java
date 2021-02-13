package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.progwards.tasktracker.model.types.WorkFlowState;

import javax.persistence.*;
import java.util.List;

/**
 * Статус, в который может быть переведена задача по ходу WorkFlowEntity
 *
 * @author Gregory Lobkov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(of = {"id"})
@Table(name = "workflow_status")
public class WorkFlowStatus {

    @Id
    @SequenceGenerator(name = "workflow_status_seq", sequenceName = "workflow_status_seq", allocationSize = 1)
    @GeneratedValue(generator = "workflow_status_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    /**
     * Родительский WF
     */
    @ManyToOne
    @JoinColumn(name = "workflow_id", referencedColumnName = "id")
    WorkFlow workflow;

    /**
     * Наименование
     */
    @Column(name = "name", nullable = false)
    String name;

    /**
     * Состояние задачи
     */
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    WorkFlowState state;

    /**
     * Действия, в которые могут быть применены к задаче с данным статусом
     */
    @OneToMany(mappedBy = "parentStatus", fetch = FetchType.LAZY)
    List<WorkFlowAction> actions;

    /**
     * На данный статус задачу можно переводить из любого состояния
     */
    @Column(name = "always_allow", nullable = false)
    Boolean alwaysAllow;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    List<Task> tasks;

    @OneToMany(mappedBy = "startStatus", fetch = FetchType.LAZY)
    List<WorkFlow> startingWorkflows;

    @OneToMany(mappedBy = "nextStatus", fetch = FetchType.LAZY)
    List<WorkFlowAction> nextedActions;


}
