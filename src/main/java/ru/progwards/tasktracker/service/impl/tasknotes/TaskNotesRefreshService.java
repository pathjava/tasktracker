package ru.progwards.tasktracker.service.impl.tasknotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskNoteEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.repository.deprecated.converter.impl.TaskNoteConverter;
import ru.progwards.tasktracker.service.RefreshService;
import ru.progwards.tasktracker.model.TaskNote;

import java.time.ZonedDateTime;

/**
 * Бизнес-логика обновления комментария
 *
 * @author Konstantin Kishkin
 */

@Service
public class TaskNotesRefreshService implements RefreshService<TaskNote> {

    private Repository<Long, TaskNoteEntity> tnRepository;
    private Converter<TaskNoteEntity, TaskNote> converterTN;

//    @Autowired
//    public void setTnRepository(TaskNoteRepository tnRepository) {
//        this.tnRepository = tnRepository;
//    }

    @Autowired
    public void setConverterTN(TaskNoteConverter converterTN) {
        this.converterTN = converterTN;
    }

    /**
     * @param taskN  value object - комментарий, который необходимо обновить
     */
    @Override
    public void refresh(TaskNote taskN) {
        taskN.setUpdated(ZonedDateTime.now());
        tnRepository.update(converterTN.toEntity(taskN));
    }
}

