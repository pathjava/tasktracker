package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.progwards.tasktracker.model.types.SystemRole;

import javax.persistence.*;
import java.util.List;

/**
 * @author Artem Dikov
 */

@Entity
@Table(name = "user_role")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRole {
    @Id
    @SequenceGenerator(name = "user_role_seq", sequenceName = "user_role_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_role_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "system_role")
    private SystemRole systemRole;

    @OneToMany(mappedBy = "userRole")
    List<AccessRule> accessRules;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role_user",
            joinColumns = { @JoinColumn(name = "user_role_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    List<User> users;

}
