package ru.progwards.tasktracker.dto;

import java.util.List;
//  ищем пример
//  TaskDtoFullConverter
//  WorkFlowActionDtoPreview
/**
 * @author Aleksandr Sidelnikov
 */
public class UserDtoFull {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<UserRoleDtoPreview> roles;

    public UserDtoFull(Long id, String name, String email, String password, List<UserRoleDtoPreview> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<UserRoleDtoPreview> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}