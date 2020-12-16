package ru.progwards.tasktracker.repository.entity;

import java.util.Random;

/**
 * Сущность для хранения типа задачи в БД
 *
 * @author Oleg Kiselev
 */
public class TaskTypeEntity {

    private Long id;
    private ProjectEntity project;
    private WorkFlowEntity workFlow;
    private String name;

    public TaskTypeEntity() {
    }

    public TaskTypeEntity(
            Long id, ProjectEntity project, WorkFlowEntity workFlow, String name
    ) {
        if (id == null) //TODO - for testing generate id
            id = new Random().nextLong();
        this.id = id;
        this.project = project;
        this.workFlow = workFlow;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public WorkFlowEntity getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlowEntity workFlow) {
        this.workFlow = workFlow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
