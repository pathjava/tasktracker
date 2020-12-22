package ru.progwards.tasktracker.service.impl.tasknotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskNoteEntity;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.model.TaskNote;

import java.time.ZonedDateTime;
import java.util.Random;

/**
 * Бизнес-логика работы с комментарием
 *
 * @author Konstantin Kishkin
 */
@Service
public class TaskNotesCreateService implements CreateService<TaskNote> {

    private Repository<Long, TaskNoteEntity> tnRepository;
    private Converter<TaskNoteEntity, TaskNote> converterTN;

    @Autowired
    public void setTaskNotesRepository(
            Repository<Long, TaskNoteEntity> tnRepository,
            Converter<TaskNoteEntity, TaskNote> converterTN
    ) {
        this.tnRepository = tnRepository;
        this.converterTN = converterTN;
    }

    /**
     * метод создает комментарий
     *
     * @param taskN value object
     */
    @Override
    public void create(TaskNote taskN) {
        if (taskN.getId() == null)
            taskN.setId(new Random().nextLong());
        if (taskN.getCreated() == null)
            taskN.setCreated(ZonedDateTime.now());
        tnRepository.create(converterTN.toEntity(taskN));
    }
}

