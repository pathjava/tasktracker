package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.progwards.tasktracker.model.types.SystemRole;

import java.util.List;

/**
 * @author Artem Dikov
 */
@AllArgsConstructor
@Data
public class UserRoleDtoFull {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
    private SystemRole systemRole;
    private List<Long> accessRules;
    private List<UserDtoPreview> userDtoPreviewList;
}
