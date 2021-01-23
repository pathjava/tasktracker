//package ru.progwards.tasktracker.repository.deprecated.entity;
//
//import ru.progwards.tasktracker.model.UserRole;
//import java.util.List;
///**
// * @author Aleksandr Sidelnikov
// */
//public class UserEntity {
//    private Long id;
//    private String name;
//    private String email;
//    private String password;
//    private List<UserRole> roles;
//    private Boolean isDeleted;
//
//    public UserEntity() {
//    }
//
//    public UserEntity(Long id, String name, String email, String password, List<UserRole> roles, Boolean isDeleted) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.roles = roles;
//        this.isDeleted = isDeleted;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public List<UserRole> getRoles() {
//        return roles;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//    //  TODO - почему отсутствуют остальные сеттеры?
//
//    public Boolean getDeleted() {
//        return isDeleted;
//    }
//
//    public void setDeleted(Boolean deleted) {
//        isDeleted = deleted;
//    }
//}