package ru.progwards.tasktracker.service.facade.impl.tasknotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.TaskNoteRepository;
import ru.progwards.tasktracker.repository.entity.TaskNoteEntity;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.TaskNote;

/**
 * Бизнес-логика удаления комментария
 *
 * @author Konstantin Kishkin
 */
@Service
public class TaskNotesDeleteService implements RemoveService<TaskNote> {

    private Repository<Long, TaskNoteEntity> tnRepository;

    @Autowired
    public void setTnRepository(TaskNoteRepository tnRepository) {
        this.tnRepository = tnRepository;
    }

    /**
     * @param taskN  value object - комментарий, который необходимо удалить
     */
    @Override
    public void remove(TaskNote taskN) {
        tnRepository.delete(taskN.getId());
    }
}
