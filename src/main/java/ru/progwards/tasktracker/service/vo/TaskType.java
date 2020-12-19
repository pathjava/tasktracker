package ru.progwards.tasktracker.service.vo;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * value object - объект бизнес логики (тип задачи)
 *
 * @author Oleg Kiselev
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "task_type")
public class TaskType {

    @Id
    @SequenceGenerator(name = "TaskTypeSeq", sequenceName = "task_type_seq", allocationSize = 1)
    @GeneratedValue(generator = "TaskTypeSeq", strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_flow_id", referencedColumnName = "id")
    private WorkFlow workFlow;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Task> tasks = new ArrayList<>();

}
