package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.RelatedTaskEntityJsonHandler;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;

import java.util.Collection;

@Component
public class RelatedTaskEntityRepository implements Repository<Long, RelatedTaskEntity> {

    private RelatedTaskEntityJsonHandler jsonHandler;

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
        RelatedTaskEntity entity = jsonHandler.relatedTasks.put(elem.getId(), elem);
        if (entity == null)
            jsonHandler.write();
    }

    @Override
    public void update(RelatedTaskEntity elem) {

    }

    @Override
    public void delete(Long id) {
        RelatedTaskEntity entity = jsonHandler.relatedTasks.get(id);
        if (entity != null) {
            for (RelatedTaskEntity value : jsonHandler.relatedTasks.values()) {
                if (value.getParentTaskId().equals(entity.getId()) && value.getTask().getId().equals(id))
                    jsonHandler.relatedTasks.remove(value.getId());
            }
            jsonHandler.relatedTasks.remove(id);
            jsonHandler.write();
        }
    }
}
