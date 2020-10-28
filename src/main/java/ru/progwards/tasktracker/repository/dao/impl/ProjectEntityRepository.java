package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.JsonHandlerProjectEntity;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ProjectEntityRepository implements Repository<Long, ProjectEntity> {

    private final JsonHandlerProjectEntity jsonHandlerProjectEntity;

    public ProjectEntityRepository(JsonHandlerProjectEntity jsonHandlerProjectEntity) {
        this.jsonHandlerProjectEntity = jsonHandlerProjectEntity;
    }

    @Override
    public Collection<ProjectEntity> get() {
        return jsonHandlerProjectEntity.getMap().values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ProjectEntity get(Long id) {
        return id == null ? null : jsonHandlerProjectEntity.getMap().get(id);
    }

    @Override
    public void create(ProjectEntity entity) {
        if (entity != null) {
            ProjectEntity newEntity = jsonHandlerProjectEntity.getMap().put(entity.getId(), entity);
            if (newEntity == null)
                jsonHandlerProjectEntity.write();
        }
    }

    @Override
    public void update(ProjectEntity entity) {
        if (entity != null) {
            delete(entity.getId());
            create(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            ProjectEntity entity = jsonHandlerProjectEntity.getMap().remove(id);
            if (entity != null)
                jsonHandlerProjectEntity.write();
        }
    }
}