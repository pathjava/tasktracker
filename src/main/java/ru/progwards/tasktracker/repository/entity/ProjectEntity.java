package ru.progwards.tasktracker.repository.entity;

public class ProjectEntity {
    private Long id;
    private String name;
    private String description;
    private String prefix;
    private Long ownerId;
    private Long created;
    private Long workFlowId;
    private Long lastTaskCode;

    public ProjectEntity(Long id, String name, String description, String prefix, Long ownerId,
                         Long created, Long workFlowId, Long lastTaskCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prefix = prefix;
        this.ownerId = ownerId;
        this.created = created;
        this.workFlowId = workFlowId;
        this.lastTaskCode = lastTaskCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrefix() {
        return prefix;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getCreated() {
        return created;
    }

    public Long getWorkFlowId() {
        return workFlowId;
    }

    public Long getLastTaskCode() {
        return lastTaskCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public void setWorkFlowId(Long workFlowId) {
        this.workFlowId = workFlowId;
    }

    public void setLastTaskCode(Long lastTaskCode) {
        this.lastTaskCode = lastTaskCode;
    }
}