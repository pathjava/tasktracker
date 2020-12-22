package ru.progwards.tasktracker.service.impl.tasknotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskNoteEntity;
import ru.progwards.tasktracker.repository.deprecated.impl.TaskNoteRepository;
import ru.progwards.tasktracker.service.RemoveService;
import ru.progwards.tasktracker.model.TaskNote;

/**
 * Бизнес-логика удаления комментария
 *
 * @author Konstantin Kishkin
 */
@Service
public class TaskNotesDeleteService implements RemoveService<TaskNote> {

    @Autowired
    private Repository<Long, TaskNoteEntity> tnRepository;

    /**
     * @param taskN  value object - комментарий, который необходимо удалить
     */
    @Override
    public void remove(TaskNote taskN) {
        tnRepository.delete(taskN.getId());
    }
}
