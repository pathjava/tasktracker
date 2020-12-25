package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * value object - объект бизнес логики (тип задачи)
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task_type")
public class TaskType {

    @Id
    @SequenceGenerator(name = "TaskTypeSeq", sequenceName = "task_type_seq", allocationSize = 1)
    @GeneratedValue(generator = "TaskTypeSeq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_flow_id", referencedColumnName = "id")
    private WorkFlow workFlow;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Task> tasks = new ArrayList<>();

}