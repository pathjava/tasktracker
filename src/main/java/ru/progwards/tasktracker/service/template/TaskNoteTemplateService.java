package ru.progwards.tasktracker.service.template;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskNote;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.service.*;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Бизнес-логика модели комментариев к задаче
 *
 * @author Konstantin Kishkin
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskNoteTemplateService implements TemplateService<TaskNote> {

    CreateService<TaskNote> taskNoteCreateService;
    /**
     * Метод создания TaskNote по шаблону
     *
     * @param args – [0] - Task, [1] - User, [2] - Integer (количество создаваемых комментариев)
     */
    @Override
    public List<TaskNote> createFromTemplate(Object... args) {
        if (args.length != 3)
            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: arguments expected");
        if (!(args[0] instanceof Task))
            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: argument 0 must be Task");
        if (!(args[1] instanceof User))
            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: argument 1 must be User");
        if (!(args[2] instanceof Integer))
            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: argument 2 must be Integer");

        int tnCount = (int) args[2];
        for (int i = 0; i < tnCount; i++) {
            TaskNote taskNote = new TaskNote();
            taskNote.setComment("Example comment");
            taskNote.setCreated(ZonedDateTime.now());
            taskNote.setUpdated(ZonedDateTime.now());
            taskNote.setAuthor((User) args[1]);
            taskNote.setTask((Task) args[0]);
            taskNote.setUpdater((User) args[1]);

            taskNoteCreateService.create(taskNote);
        }

        return Collections.emptyList();
    }
}

