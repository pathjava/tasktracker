package ru.progwards.tasktracker.service.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.Task;

/**
 * Бизнес-логика получения задачи по идентификатору
 *
 * @author Oleg Kiselev
 */
@Service
@Deprecated
public class TaskGetService /*implements GetService<Long, Task>*/ {

////    @Autowired
//    private Repository<Long, TaskEntity> repository;
////    @Autowired
//    private Converter<TaskEntity, Task> converter;
//
//    /**
//     * Метод получения задачи по идентификатору
//     *
//     * @param id идентификатор по которому необходимо получить задачу
//     * @return найденную задачу или пусто
//     */
//    @Override
//    public Task get(Long id) {
//        return id == null ? null : converter.toVo(repository.get(id));
//        return null;
//    }
}
