package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.progwards.tasktracker.model.types.AccessObject;
import ru.progwards.tasktracker.model.types.AccessType;

import javax.persistence.*;

/**
 * @author Artem Dikov, Konstantin Kishkin
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString(of = {"id"})
@Table(name = "access_rule")
public class AccessRule {
    @Id
    @SequenceGenerator(name = "access_rule_seq", sequenceName = "access_rule_seq", allocationSize = 1)
    @GeneratedValue(generator = "access_rule_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_role", referencedColumnName = "id")
    UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "object")
    AccessObject object;

    @Column(name = "property_name")
    private String propertyName; // null == all

    @Column(name = "object_id")
    private Long objectId; // null == all

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type")
    private AccessType accessType;

}
