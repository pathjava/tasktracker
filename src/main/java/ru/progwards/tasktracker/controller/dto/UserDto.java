package ru.progwards.tasktracker.controller.dto;

import ru.progwards.tasktracker.service.vo.UserRole;
import java.util.List;

/**
 * @author Aleksandr Sidelnikov
 */
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<UserRole> roles;

    public UserDto(Long id, String name, String email, String password, List<UserRole> roles) {
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

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}