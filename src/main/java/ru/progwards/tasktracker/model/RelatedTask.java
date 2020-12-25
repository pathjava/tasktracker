package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * value object - объект бизнес логики (связанная задача)
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "related_task")
public class RelatedTask {

    @Id
    @SequenceGenerator(name = "RelatedTaskSeq", sequenceName = "related_task_seq", allocationSize = 1)
    @GeneratedValue(generator = "RelatedTaskSeq", strategy = GenerationType.SEQUENCE)
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

//    @Type(type = "true_false")
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

}
