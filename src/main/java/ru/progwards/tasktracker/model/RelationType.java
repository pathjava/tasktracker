package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * value object - объект бизнес логики (тип связи)
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "relation_type")
public class RelationType {

    @Id
    @SequenceGenerator(name = "RelationTypeSeq", sequenceName = "relation_type_seq", allocationSize = 1)
    @GeneratedValue(generator = "RelationTypeSeq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counter_id", referencedColumnName = "id")
    private RelationType counterRelation;

    @OneToMany(mappedBy = "relationType", fetch = FetchType.LAZY)
    private List<RelatedTask> relatedTasks = new ArrayList<>();

}