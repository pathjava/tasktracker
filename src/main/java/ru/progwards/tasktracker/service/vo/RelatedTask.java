package ru.progwards.tasktracker.service.vo;

import lombok.*;

import javax.persistence.*;

/**
 * value object - объект бизнес логики (связанная задача)
 *
 * @author Oleg Kiselev
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "related_task")
public class RelatedTask {

    @Id
    @SequenceGenerator(name = "RelatedTaskSeq", sequenceName = "related_task_seq", allocationSize = 1)
    @GeneratedValue(generator = "RelatedTaskSeq", strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "counter_type_id", referencedColumnName = "id")
    private RelationType relationType;

    @ManyToOne //TODO fetch - ?
    @JoinColumn(name = "current_task_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private Task currentTask;

    @ManyToOne
    @JoinColumn(name = "attached_task_id", referencedColumnName = "id")
    private Task attachedTask;

    /*boolean isDeleted;*/ //TODO

}
