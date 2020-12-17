package ru.progwards.tasktracker.service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Entity
@Table(name = "related_task")
public class RelatedTask {

    @Id
    @SequenceGenerator(name = "related_task_seq", sequenceName = "related_task_seq", allocationSize = 1)
    @GeneratedValue(generator = "related_task_seq", strategy = GenerationType.SEQUENCE)
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

}
