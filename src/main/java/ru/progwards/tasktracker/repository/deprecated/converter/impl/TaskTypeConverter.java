//package ru.progwards.tasktracker.repository.deprecated.converter.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
//import ru.progwards.tasktracker.repository.deprecated.entity.ProjectEntity;
//import ru.progwards.tasktracker.repository.deprecated.entity.TaskTypeEntity;
//import ru.progwards.tasktracker.repository.deprecated.entity.WorkFlowEntity;
//import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
//import ru.progwards.tasktracker.model.Project;
//import ru.progwards.tasktracker.model.TaskType;
//import ru.progwards.tasktracker.model.WorkFlow;
//
///**
// * Конвертеры valueObject <-> entity
// *
// * @author Oleg Kiselev
// */
//@Component
//@Deprecated
//public class TaskTypeConverter implements Converter<TaskTypeEntity, TaskType> {
//
//    @Autowired
//    private Repository<Long, TaskTypeEntity> taskTypeRepository;
//    @Autowired
//    private Converter<ProjectEntity, Project> projectConverter;
//    @Autowired
//    private Converter<WorkFlowEntity, WorkFlow> workFlowConverter;
//
//    /**
//     * Метод конвертирует сущность Entity в бизнес объект
//     *
//     * @param entity сущность, полученная из БД
//     * @return value object - объект бизнес логики
//     */
//    @Override
//    public TaskType toVo(TaskTypeEntity entity) {
////        if (entity == null)
////            return null;
////        else {
////            return new TaskType(
////                    entity.getId(),
////                    projectConverter.toVo(entity.getProject()),
////                    workFlowConverter.toVo(entity.getWorkFlow()),
////                    entity.getName(),
////                    null //TODO - check
////            );
////        }
//        return null;
//    }
//
//    /**
//     * Метод конвертирует бизнес объект в сущность Entity
//     *
//     * @param valueObject value object - объект бизнес логики
//     * @return сущность для БД
//     */
//    @Override
//    public TaskTypeEntity toEntity(TaskType valueObject) {
////        if (valueObject == null)
////            return null;
////        else {
////            TaskTypeEntity taskTypeEntity = taskTypeRepository.get(valueObject.getId());
////            return new TaskTypeEntity(
////                    valueObject.getId(),
////                    projectConverter.toEntity(valueObject.getProject()),
////                    workFlowConverter.toEntity(valueObject.getWorkFlow()),
////                    valueObject.getName()
////            );
////        }
//        return null;
//    }
//}