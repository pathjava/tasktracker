package ru.progwards.tasktracker.repository.deprecated.entity;

import ru.progwards.tasktracker.model.types.AccessType;

public class AccessRuleEntity {
    private Long id;
    private String objectName;
    private String propertyName; // null == all
    private Long objectId; // null == all
    private AccessType accessType;

    public AccessRuleEntity(Long id, String objectName, String propertyName, Long objectId, AccessType accessType) {
        this.id = id;
        this.objectName = objectName;
        this.propertyName = propertyName;
        this.objectId = objectId;
        this.accessType = accessType;
    }

    public void setId(Long id) {
        this.id = id;
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
}
