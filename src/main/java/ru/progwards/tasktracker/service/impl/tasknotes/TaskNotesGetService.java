package ru.progwards.tasktracker.service.impl.tasknotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskNoteEntity;
import ru.progwards.tasktracker.repository.deprecated.impl.TaskNoteRepository;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.TaskNote;

/**
 * Бизнес-логика получения комментария
 *
 *
 * @author Konstantin Kishkin
 */
@Service
public class TaskNotesGetService implements GetService<Long, TaskNote> {

    private Repository<Long, TaskNoteEntity> tnRepository;
//    private Converter<TaskNoteEntity, TaskNote> converterTN;

    @Autowired
    public void setTNRepository(TaskNoteRepository tnRepository) {
        this.tnRepository = tnRepository;
    }

//    @Autowired
//    public void setConverterTN(TaskNoteConverter converterTN) {
//        this.converterTN = converterTN;
//    }

    /**
     * @param id идентификатор по которому необходимо получить комментарий
     * @return найденный комментарий или пусто
     */
    @Override
    public TaskNote get(Long id) {
//        return id == null ? null : converterTN.toVo(tnRepository.get(id));
        return null;
    }
}
