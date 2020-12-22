package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.model.UpdateOneValue;
import ru.progwards.tasktracker.model.User;

import java.lang.reflect.Field;
import java.util.Collection;

@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
public class UserRepository implements Repository<Long, User>, RepositoryUpdateField<User> {

//    @Autowired
//    private TaskTypeRepository<User, Long> repository;

    /**
     * @return
     */
    @Override
    public Collection<User> get() {
//        return UserJsonHandler.getMap().values().stream().collect(Collectors.toUnmodifiableList());
        return null;
    }

    /**
     * @param id идентификатор
     * @return
     */
    @Override
    public User get(Long id) {
//        return id == null ? null : UserJsonHandler.getMap().get(id);
        return null;
    }

    /**
     * @param entity новая сущность
     */
    @Override
    public void create(User entity) {
//        if (entity != null) {
//            if (entity.getId() == null)
//                entity.setId(new Random().nextLong());
//            User newEntity = UserJsonHandler.getMap().put(entity.getId(), entity);
//            if (newEntity == null)
//                UserJsonHandler.write();
//        }
    }

    /**
     * @param entity измененная сущность
     */
    @Override
    public void update(User entity) {
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
//            User entity = UserJsonHandler.getMap().remove(id);
//            if (entity != null)
//                UserJsonHandler.write();
//        }
    }

    /**
     * @param oneValue
     */
    @Override
    public void updateField(UpdateOneValue oneValue) {
        User entity = get(oneValue.getId());
        String field = oneValue.getFieldName();

        Class<User> clazz = User.class;

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