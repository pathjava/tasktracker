package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.WorkLog;

/**
 * Бизнес-логика удаления одного лога
 *
 * @author Oleg Kiselev
 */
@Service
public class WorkLogRemoveService implements RemoveService<WorkLog> {

    private Repository<Long, WorkLogEntity> repository;

    @Autowired
    public void setRepository(Repository<Long, WorkLogEntity> repository) {
        this.repository = repository;
    }

    /**
     * @param model объект, который необходимо удалить
     */
    @Override
    public void remove(WorkLog model) {
        repository.delete(model.getId());
    }
}
