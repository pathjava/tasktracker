//package ru.progwards.tasktracker.repository.deprecated.impl;
//
//import org.springframework.stereotype.Component;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
//import ru.progwards.tasktracker.repository.deprecated.jsonhandler.UserEntityJsonHandler;
//import ru.progwards.tasktracker.repository.deprecated.entity.UserEntity;
//
//import java.util.Collection;
//import java.util.Random;
//import java.util.stream.Collectors;
//
//@Component
//public class UserEntityRepository implements Repository<Long, UserEntity> {
//
//    private final UserEntityJsonHandler userEntityJsonHandler;
//
//    public UserEntityRepository(UserEntityJsonHandler userEntityJsonHandler) {
//        this.userEntityJsonHandler = userEntityJsonHandler;
//    }
//
//    @Override
//    public Collection<UserEntity> get() {
//        return userEntityJsonHandler.getMap().values().stream().collect(Collectors.toUnmodifiableList());
//    }
//
//    @Override
//    public UserEntity get(Long id) {
//        return id == null ? null : userEntityJsonHandler.getMap().get(id);
//    }
//
//    @Override
//    public void create(UserEntity entity) {
//        if (entity != null) {
//            if (entity.getId() == null)
//                entity.setId(new Random().nextLong());
//            UserEntity newEntity = userEntityJsonHandler.getMap().put(entity.getId(), entity);
//            if (newEntity == null)
//                userEntityJsonHandler.write();
//        }
//    }
//
//    @Override
//    public void update(UserEntity entity) {
//        if (entity != null) {
//            delete(entity.getId());
//            create(entity);
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//        if (id != null) {
//            UserEntity entity = userEntityJsonHandler.getMap().remove(id);
//            if (entity != null)
//                userEntityJsonHandler.write();
//        }
//    }
//}