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
@Table(name = "UserRole")
@AllArgsConstructor
@Data
public class UserRole {
    @Id
    @SequenceGenerator(name = "USERROLESEQ", sequenceName = "userroleseq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "USERROLESEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "system_role")
    private SystemRole systemRole;

    @OneToMany(mappedBy = "")
    List<AccessRule> accessRules; //full:List<AccessRuleDtoFull>

    //@ManyToMany
    List<User> users; //noDto

//    public UserRole(Long id, String name, SystemRole systemRole, Map<Long, AccessRule> accessRules) {
//        this.id = id;
//        this.name = name;
//        this.systemRole = systemRole;
//        this.accessRules = accessRules;
//    }

//    public Long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public SystemRole getSystemRole() {
//        return systemRole;
//    }
//
//    public void setSystemRole(SystemRole systemRole) {
//        this.systemRole = systemRole;
//    }
//
//    public Map<Long, AccessRule> getAccessRules() {
//        return accessRules;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserRole userRole = (UserRole) o;
//        return Objects.equals(id, userRole.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
