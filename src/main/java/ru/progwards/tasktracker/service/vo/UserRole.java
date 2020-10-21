package ru.progwards.tasktracker.service.vo;

import ru.progwards.tasktracker.util.types.SystemRole;

import java.util.List;

public class UserRole {
    private final Long id;
    private String name;
    private SystemRole systemRole;
    private List<AccessRule> accessRules;

    public UserRole(Long id, String name, SystemRole systemRole, List<AccessRule> accessRules) {
        this.id = id;
        this.name = name;
        this.systemRole = systemRole;
        this.accessRules = accessRules;
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

    public List<AccessRule> getAccessRules() {
        return accessRules;
    }
}
