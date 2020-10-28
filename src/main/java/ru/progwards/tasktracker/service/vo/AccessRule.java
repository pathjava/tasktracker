package ru.progwards.tasktracker.service.vo;

import ru.progwards.tasktracker.util.types.AccessType;

import java.util.Objects;

public class AccessRule {
    private Long id;
    private String objectName;
    private String propertyName; // null == all
    private Long objectId; // null == all
    private AccessType accessType;

    public AccessRule(Long id, String objectName, String propertyName, Long objectId, AccessType accessType) {
        this.id = id;
        this.objectName = objectName;
        this.propertyName = propertyName;
        this.objectId = objectId;
        this.accessType = accessType;
    }

    public Long getId() {
        return id;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessRule that = (AccessRule) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
