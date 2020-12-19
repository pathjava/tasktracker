package ru.progwards.tasktracker.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.progwards.tasktracker.util.types.AccessType;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Data
public class AccessRule {
    @Id
    @SequenceGenerator(name = "ACCESSRULESEQ", sequenceName = "accessruleseq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "ACCESSRULESEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Basic
    @Column(name = "object_name")
    private String objectName;

    @Basic
    @Column(name = "property_name")
    private String propertyName; // null == all

    @Basic
    @Column(name = "object_id")
    private Long objectId; // null == all

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type")
    private AccessType accessType;

    @ManyToOne
    @JoinColumn(name = "user_role", referencedColumnName = "id")
    UserRole userRole;



//    public AccessRule(Long id, String objectName, String propertyName, Long objectId, AccessType accessType) {
//        this.id = id;
//        this.objectName = objectName;
//        this.propertyName = propertyName;
//        this.objectId = objectId;
//        this.accessType = accessType;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getObjectName() {
//        return objectName;
//    }
//
//    public void setObjectName(String objectName) {
//        this.objectName = objectName;
//    }
//
//    public String getPropertyName() {
//        return propertyName;
//    }
//
//    public void setPropertyName(String propertyName) {
//        this.propertyName = propertyName;
//    }
//
//    public Long getObjectId() {
//        return objectId;
//    }
//
//    public void setObjectId(Long objectId) {
//        this.objectId = objectId;
//    }
//
//    public AccessType getAccessType() {
//        return accessType;
//    }
//
//    public void setAccessType(AccessType accessType) {
//        this.accessType = accessType;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AccessRule that = (AccessRule) o;
//        return Objects.equals(id, that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
