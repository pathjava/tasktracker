//package ru.progwards.tasktracker.repository.deprecated.converter.impl;
//
//import org.springframework.stereotype.Component;
//import ru.progwards.tasktracker.model.TaskAttachmentContent;
//import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
//import ru.progwards.tasktracker.repository.deprecated.entity.AttachmentContentEntity;
//
//
///**
// * Преобразование valueObject <-> entity
// *
// * AttachmentContent <-> AttachmentContentEntity
// *
// * @author Gregory Lobkov
// */
//@Component
//@Deprecated
//public class AttachmentContentConverter implements Converter<AttachmentContentEntity, TaskAttachmentContent> {
//
//
//    /**
//     * Преобразовать в бизнес-объект
//     *
//     * @param entity сущность репозитория
//     * @return бизнес-объект
//     */
//    @Override
//    public TaskAttachmentContent toVo(AttachmentContentEntity entity) {
////        byte[] bytes = entity.getData();
////        InputStream targetStream = new ByteArrayInputStream(bytes);
////        return new AttachmentContent(entity.getId(), targetStream);
//        return null;
//    }
//
//
//    /**
//     * Преобразовать в сущность хранилища
//     *
//     * @param vo бизнес-объект
//     * @return сущность репозитория
//     */
//    @Override
//    public AttachmentContentEntity toEntity(TaskAttachmentContent vo) {
////        try {
////            return new AttachmentContentEntity(vo.getId(), vo.getData().readAllBytes());
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        return null;
//    }
//
//}
