package ru.progwards.tasktracker.service.vo;

import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

/**
 * Бизнес-процесс
 * Формирует дерево движения задачи по статусам
 *
 * @author Gregory Lobkov
 */
@Entity
@Table(name = "workflow")
public class WorkFlow {

    @Id
    @SequenceGenerator(name = "workflow_seq", sequenceName = "workflow_seq", allocationSize = 1)
    @GeneratedValue(generator = "workflow_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    /**
     * Ноименование
     */
    @Column(name = "name", nullable = false)
    String name;

    /**
     * Признак шаблона
     *
     * Чтобы легко можно было отличить воркфлоу конкретного процесса
     * от шаблона, на основе которого будет все создаваться,
     * иначе его нельзя будет настраивать индивидуально
     */
    @Column(name = "pattern", nullable = false)
    boolean pattern;

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
    @OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY)
    List<TaskType> taskTypes;

    public WorkFlow() { }

    public WorkFlow(Long id, String name, boolean pattern, WorkFlowStatus startStatus, List<WorkFlowStatus> statuses, List<TaskType> taskTypes) {
        this.id = id;
        this.name = name;
        this.pattern = pattern;
        this.startStatus = startStatus;
        this.statuses = statuses;
        this.taskTypes = taskTypes;
    }

    public List<WorkFlowStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<WorkFlowStatus> statuses) {
        this.statuses = statuses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPattern() {
        return pattern;
    }

    public void setPattern(boolean pattern) {
        this.pattern = pattern;
    }

    public WorkFlowStatus getStartStatus() {
        return startStatus;
    }

    public void setStartStatus(WorkFlowStatus startStatus) {
        this.startStatus = startStatus;
    }

    public List<TaskType> getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(List<TaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }
}
