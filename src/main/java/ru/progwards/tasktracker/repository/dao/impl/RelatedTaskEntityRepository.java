package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;

import java.util.Collection;

@Component
public class RelatedTaskEntityRepository implements Repository<Long, RelatedTaskEntity> {
    @Override
    public Collection<RelatedTaskEntity> get() {
        return null;
    }

    @Override
    public RelatedTaskEntity get(Long id) {
        return null;
    }

    @Override
    public void create(RelatedTaskEntity elem) {

    }

    @Override
    public void update(RelatedTaskEntity elem) {

    }

    @Override
    public void delete(Long id) {

    }
}
