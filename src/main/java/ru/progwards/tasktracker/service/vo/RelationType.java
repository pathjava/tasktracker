package ru.progwards.tasktracker.service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * value object - объект бизнес логики (тип связи)
 *
 * @author Oleg Kiselev
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "relation_type")
public class RelationType {

    @Id
    @SequenceGenerator(name = "relation_type_seq", sequenceName = "relation_type_seq", allocationSize = 1)
    @GeneratedValue(generator = "relation_type_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counter_id", referencedColumnName = "id")
    private RelationType counterRelation;

    @OneToMany(mappedBy = "relationType", fetch = FetchType.LAZY)
    private List<RelatedTask> relatedTasks;

}
