package ru.progwards.tasktracker.service.facade.impl.tasknotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.TaskNoteRepository;
import ru.progwards.tasktracker.repository.entity.TaskNoteEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.repository.converter.impl.TaskNoteConverter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.TaskNote;

/**
 * Бизнес-логика получения комментария
 *
 *
 * @author Konstantin Kishkin
 */
@Service
public class TaskNotesGetService implements GetService<Long, TaskNote> {

    private Repository<Long, TaskNoteEntity> tnRepository;
    private Converter<TaskNoteEntity, TaskNote> converterTN;

    @Autowired
    public void setTNRepository(TaskNoteRepository tnRepository) {
        this.tnRepository = tnRepository;
    }

    @Autowired
    public void setConverterTN(TaskNoteConverter converterTN) {
        this.converterTN = converterTN;
    }

    /**
     * @param id идентификатор по которому необходимо получить комментарий
     * @return найденный комментарий или пусто
     */
    @Override
    public TaskNote get(Long id) {
        return id == null ? null : converterTN.toVo(tnRepository.get(id));
    }
}
