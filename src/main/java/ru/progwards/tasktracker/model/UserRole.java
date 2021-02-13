package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.progwards.tasktracker.model.types.SystemRole;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Artem Dikov
 */

@Entity
@ToString(of = {"id"})
@Table(name = "user_role")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRole {
    @Id
    @SequenceGenerator(name = "user_role_seq", sequenceName = "user_role_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_role_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "system_role")
    private SystemRole systemRole;

    @OneToMany(mappedBy = "userRole")
    List<AccessRule> accessRules;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role_user",
            joinColumns = {@JoinColumn(name = "user_role_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    List<User> users;

}
