//package ru.progwards.tasktracker.repository.deprecated.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
//import ru.progwards.tasktracker.repository.deprecated.entity.TaskPriorityEntity;
//
//import java.util.Collection;
//import java.util.Random;
//import java.util.stream.Collectors;
//
///**
// * Репозиторий TaskPriorityEntity
// * @author Pavel Khovaylo
// */
//@org.springframework.stereotype.Repository
//public class TaskPriorityEntityRepository implements Repository<Long, TaskPriorityEntity> {
//
//    @Autowired
//    private JsonHandler<Long, TaskPriorityEntity> taskPriorityEntityJsonHandler;
//
//    @Override
//    public Collection<TaskPriorityEntity> get() {
//        return taskPriorityEntityJsonHandler.getMap().values().stream().collect(Collectors.toUnmodifiableList());
//    }
//
//    @Override
//    public TaskPriorityEntity get(Long id) {
//        return id == null ? null : taskPriorityEntityJsonHandler.getMap().get(id);
//    }
//
//    @Override
//    public void create(TaskPriorityEntity entity) {
//        if (entity != null) {
//            if (entity.getId() == null)
//                entity.setId(new Random().nextLong());
//            TaskPriorityEntity newEntity = taskPriorityEntityJsonHandler.getMap().put(entity.getId(), entity);
//            if (newEntity == null)
//                taskPriorityEntityJsonHandler.write();
//        }
//    }
//
//    @Override
//    public void update(TaskPriorityEntity entity) {
//        if (entity != null) {
//            delete(entity.getId());
//            create(entity);
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//        if (id != null) {
//            TaskPriorityEntity entity = taskPriorityEntityJsonHandler.getMap().remove(id);
//            if (entity != null)
//                taskPriorityEntityJsonHandler.write();
//        }
//    }
//}