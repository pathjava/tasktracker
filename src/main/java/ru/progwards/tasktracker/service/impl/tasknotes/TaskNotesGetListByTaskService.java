package ru.progwards.tasktracker.service.impl.tasknotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskNoteEntity;
import ru.progwards.tasktracker.service.GetListByTaskService;
import ru.progwards.tasktracker.model.TaskNote;

import java.util.Collection;

/**
 * Бизнес-логика получения списка комментариев по задаче
 *
 * @author Konstantin Kishkin
 */
@Service
public class TaskNotesGetListByTaskService implements GetListByTaskService<Long, TaskNote> {

    private RepositoryByTaskId<Long, TaskNoteEntity> repository;
    private Converter<TaskNoteEntity, TaskNote> converter;

    @Autowired
    public void setRepository(RepositoryByTaskId<Long, TaskNoteEntity> repository) {
        this.repository = repository;
    }

//    @Autowired
//    public void setConverter(Converter<TaskNote, TaskNote> converter) {
//        this.converter = converter;
//    }

    /**
     * @param taskId идентификатор задачи для которой необходимо получить комментарии
     * @return коллекция объектов комментариев
     */
    @Override
    public Collection<TaskNote> getListByTaskId(Long taskId) {
//        return repository.getByTaskId(taskId).stream()
//                .filter(TaskNotesEntity -> TaskNotesEntity.getTask_id().equals(taskId))
//                .map(TaskNotesEntity -> converter.toVo(TaskNotesEntity))
//                .collect(Collectors.toList());
        return null;
    }
}

