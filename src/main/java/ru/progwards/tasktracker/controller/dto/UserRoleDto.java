package ru.progwards.tasktracker.controller.dto;

import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.util.types.SystemRole;

import java.util.List;

public class UserRoleDto {
    private Long id;
    private String name;
    private SystemRole systemRole;
    private List<Long> accessRules;

    public UserRoleDto(Long id, String name, SystemRole systemRole, List<Long> accessRules) {
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

    public List<Long> getAccessRules() {
        return accessRules;
    }
}
