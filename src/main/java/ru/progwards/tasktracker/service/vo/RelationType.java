package ru.progwards.tasktracker.service.vo;

import javax.persistence.*;
import java.util.List;

/**
 * value object - объект бизнес логики (тип связи)
 *
 * @author Oleg Kiselev
 */
@Entity
@Table(name = "relation_type")
public class RelationType {

    @Id
    @SequenceGenerator(name = "relation_type_seq", sequenceName = "relation_type_seq", allocationSize = 1)
    @GeneratedValue(generator = "relation_type_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counter_id", referencedColumnName = "id")
    private RelationType counterRelation;

    @OneToMany(mappedBy = "relationType", fetch = FetchType.LAZY)
    private List<RelatedTask> relatedTasks;

    public RelationType(Long id, String name, RelationType counterRelation, List<RelatedTask> relatedTasks) {
        this.id = id;
        this.name = name;
        this.counterRelation = counterRelation;
        this.relatedTasks = relatedTasks;
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

    public RelationType getCounterRelation() {
        return counterRelation;
    }

    public void setCounterRelation(RelationType counterRelation) {
        this.counterRelation = counterRelation;
    }

    public List<RelatedTask> getRelatedTasks() {
        return relatedTasks;
    }

    public void setRelatedTasks(List<RelatedTask> relatedTasks) {
        this.relatedTasks = relatedTasks;
    }
}
