package ru.progwards.tasktracker.service.vo;

import ru.progwards.tasktracker.util.types.SystemRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserRole {
    private Long id;
    private String name;
    private SystemRole systemRole;
    private Map<Long, AccessRule> accessRules;

    public UserRole(Long id, String name, SystemRole systemRole, Map<Long, AccessRule> accessRules) {
        this.id = id;
        this.name = name;
        this.systemRole = systemRole;
        this.accessRules = accessRules;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SystemRole getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    public Map<Long, AccessRule> getAccessRules() {
        return accessRules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
