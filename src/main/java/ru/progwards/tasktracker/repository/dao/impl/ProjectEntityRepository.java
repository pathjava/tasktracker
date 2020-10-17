package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class ProjectEntityRepository implements Repository<Long, ProjectEntity> {

    @Autowired
    private JsonHandlerProjectEntity jsonHandlerProjectEntity;

    @Override
    public Collection<ProjectEntity> get() {
        return jsonHandlerProjectEntity.getMap().values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ProjectEntity get(Long id) {
        return jsonHandlerProjectEntity.getMap().get(id);
    }

    @Override
    public void create(ProjectEntity entity) {
        ProjectEntity newEntity = jsonHandlerProjectEntity.getMap().put(entity.getId(), entity);
        if (newEntity == null)
            jsonHandlerProjectEntity.write();
    }

    @Override
    public void update(ProjectEntity entity) {
        delete(entity.getId());
        create(entity);
    }

    @Override
    public void delete(Long id) {
        ProjectEntity entity = jsonHandlerProjectEntity.getMap().remove(id);
        if (entity != null)
            jsonHandlerProjectEntity.write();
        else
            throw new NoSuchElementException("Element with id=" + id + " not exist");
    }
}