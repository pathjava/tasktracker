package ru.progwards.tasktracker.service.vo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * value object - объект бизнес логики (тип задачи)
 *
 * @author Oleg Kiselev
 */
@Entity
@Table(name = "task_type")
public class TaskType {

    @Id
    @SequenceGenerator(name = "task_type_seq", sequenceName = "task_type_seq", allocationSize = 1)
    @GeneratedValue(generator = "task_type_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_flow_id", referencedColumnName = "id")
    private WorkFlow workFlow;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Task> tasks = new ArrayList<>();

    public TaskType(Long id, Project project, WorkFlow workFlow, String name, List<Task> tasks) {
        this.id = id;
        this.project = project;
        this.workFlow = workFlow;
        this.name = name;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
