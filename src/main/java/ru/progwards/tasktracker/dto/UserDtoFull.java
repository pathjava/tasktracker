package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
//  ищем пример
//  TaskDtoFullConverter
//  WorkFlowActionDtoPreview
/**
 * @author Aleksandr Sidelnikov
 */
@Data
@AllArgsConstructor
public class UserDtoFull {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<UserRoleDtoPreview> roles;

}