package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.UpdateOneValue;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Репозиторий проектов
 * @author Pavel Khovaylo
 */
@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
public class ProjectRepository implements Repository<Long, Project>, RepositoryUpdateField<Project> {

//    @Autowired
//    private TaskTypeRepository<Project, Long> repository;

    /**
     * @return
     */
    @Override
    public Collection<Project> get() {
//        return ProjectJsonHandler.getMap().values().stream().collect(Collectors.toUnmodifiableList());
        return null;
    }

    /**
     * @param id идентификатор
     * @return
     */
    @Override
    public Project get(Long id) {
//        return id == null ? null : ProjectJsonHandler.getMap().get(id);
        return null;
    }

    /**
     * @param entity новая сущность
     */
    @Override
    public void create(Project entity) {
//        if (entity != null) {
//            Project newEntity = ProjectJsonHandler.getMap().put(entity.getId(), entity);
//            if (newEntity == null)
//                ProjectJsonHandler.write();
//        }
    }

    /**
     * @param entity измененная сущность
     */
    @Override
    public void update(Project entity) {
        if (entity != null) {
            delete(entity.getId());
            create(entity);
        }
    }

    /**
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
//        if (id != null) {
//            Project entity = ProjectJsonHandler.getMap().remove(id);
//            if (entity != null)
//                ProjectJsonHandler.write();
//        }
    }

    /**
     * метод позволяет обновить значение любого поля проекта
     * @param oneValue объект с данными по текущему обновлению
     */
    @Override
    public void updateField(UpdateOneValue oneValue) {
        Project entity = get(oneValue.getId());
        String field = oneValue.getFieldName();

        Class<Project> clazz = Project.class;

        for (Field f : clazz.getDeclaredFields()) {
            if (f.getName().equals(field)) {
                f.setAccessible(true);
                try {
                    f.set(entity, oneValue.getNewValue());
                    update(entity);
                    break;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}