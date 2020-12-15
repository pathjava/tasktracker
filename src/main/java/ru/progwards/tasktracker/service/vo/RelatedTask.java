package ru.progwards.tasktracker.service.vo;

import javax.persistence.*;

/**
 * value object - объект бизнес логики (связанная задача)
 *
 * @author Oleg Kiselev
 */
@Entity
@Table(name = "related_tasks")
public class RelatedTask {

    @Id
    @SequenceGenerator(name = "related_task_seq", sequenceName = "related_tasks_seq", allocationSize = 1)
    @GeneratedValue(generator = "related_task_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "counter_type_id", referencedColumnName = "id")
    private RelationType relationType;

    @ManyToOne //TODO fetch - ?
    @JoinColumn(name = "current_task_id", referencedColumnName = "id")
    private Task currentTask;

    @ManyToOne
    @JoinColumn(name = "attached_task_id", referencedColumnName = "id")
    private Task attachedTask;

    /*boolean isDeleted;*/ //TODO

    public RelatedTask(Long id, RelationType relationType, Task currentTask, Task attachedTask) {
        this.id = id;
        this.relationType = relationType;
        this.currentTask = currentTask;
        this.attachedTask = attachedTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public Task getAttachedTask() {
        return attachedTask;
    }

    public void setAttachedTask(Task attachedTask) {
        this.attachedTask = attachedTask;
    }
}
