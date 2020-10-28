package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.RelatedTaskEntityJsonHandler;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;

import java.util.Collection;

@Component
public class RelatedTaskEntityRepository implements Repository<Long, RelatedTaskEntity> {

    private JsonHandler<Long, RelatedTaskEntity> jsonHandler;

    @Autowired
    public void setJsonHandler(RelatedTaskEntityJsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

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
        RelatedTaskEntity entity = jsonHandler.getMap().put(elem.getId(), elem);
        if (entity == null)
            jsonHandler.write();
    }

    @Override
    public void update(RelatedTaskEntity elem) {

    }

    @Override
    public void delete(Long id) {
        RelatedTaskEntity entity = jsonHandler.getMap().get(id);
        if (entity != null) {
            for (RelatedTaskEntity value : jsonHandler.getMap().values()) {
                if (value.getParentTaskId().equals(entity.getId()) && value.getTaskId().equals(id))
                    jsonHandler.getMap().remove(value.getId());
            }
            jsonHandler.getMap().remove(id);
            jsonHandler.write();
        }
    }
}
