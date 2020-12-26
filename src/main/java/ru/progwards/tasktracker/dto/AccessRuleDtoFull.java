package ru.progwards.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.progwards.tasktracker.model.types.AccessType;

/**
 * @author Artem Dikov
 */
@AllArgsConstructor
@Data
public class AccessRuleDtoFull {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String objectName;
    private String propertyName; // null == all
    private Long objectId; // null == all
    private AccessType accessType;
    private Long userRoleId;
}
