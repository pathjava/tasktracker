//package ru.progwards.tasktracker.repository.deprecated.converter.impl;
//
//import org.springframework.stereotype.Component;
//import ru.progwards.tasktracker.model.User;
//import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
//import ru.progwards.tasktracker.repository.deprecated.entity.UserEntity;
//
///**
// * Преобразование valueObject <-> entity
// * User <-> UserEntity
// * @author Aleksandr Sidelnikov
// */
//@Component
//@Deprecated
//public class UserConverter implements Converter<UserEntity, User> {
//
////    /**
////     * Сервис получения статусов User
////     */
////    @Autowired
////    private GetService<Long, UserStatus> userStatusGetService;
//
//    /**
//     * Преобразовать в бизнес-объект
//     *
//     * @param entity сущность репозитория
//     * @return бизнес-объект
//     */
//    @Override
//    public User toVo(UserEntity entity) {
//        if (entity == null)
//            return null;
////        UserStatus userStatus = userStatusGetService.get(entity.getStart_status_id()); // должно стать lazy load в будущем
////        return new User(
////                entity.getId(),
////                entity.getName(),
////                entity.getEmail(),
////                entity.getPassword(),
////                entity.getRoles());
//        return null;
//    }
//
//    /**
//     * Преобразовать в сущность хранилища
//     *
//     * @param vo бизнес-объект
//     * @return сущность репозитория
//     */
//    @Override
//    public UserEntity toEntity(User vo) {
////        return new UserEntity(vo.getId(), vo.getName(), vo.getEmail(), vo.getPassword(), vo.getRoles(), false);
//        return null;
//    }
//}