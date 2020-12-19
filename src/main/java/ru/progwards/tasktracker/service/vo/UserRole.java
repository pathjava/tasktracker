package ru.progwards.tasktracker.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.progwards.tasktracker.util.types.SystemRole;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "user_role")
@AllArgsConstructor
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
