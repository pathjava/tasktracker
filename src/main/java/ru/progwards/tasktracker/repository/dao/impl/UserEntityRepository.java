package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.JsonHandlerUserEntity;
import ru.progwards.tasktracker.repository.entity.UserEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserEntityRepository implements Repository<Long, UserEntity> {

    private final JsonHandlerUserEntity jsonHandlerUserEntity;

    public UserEntityRepository(JsonHandlerUserEntity jsonHandlerUserEntity) {
        this.jsonHandlerUserEntity = jsonHandlerUserEntity;
    }

    @Override
    public Collection<UserEntity> get() {
        return jsonHandlerUserEntity.getMap().values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public UserEntity get(Long id) {
        return id == null ? null : jsonHandlerUserEntity.getMap().get(id);
    }

    @Override
    public void create(UserEntity entity) {
        if (entity != null) {
            UserEntity newEntity = jsonHandlerUserEntity.getMap().put(entity.getId(), entity);
            if (newEntity == null)
                jsonHandlerUserEntity.write();
        }
    }

    @Override
    public void update(UserEntity entity) {
        if (entity != null) {
            delete(entity.getId());
            create(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            UserEntity entity = jsonHandlerUserEntity.getMap().remove(id);
            if (entity != null)
                jsonHandlerUserEntity.write();
        }
    }
}