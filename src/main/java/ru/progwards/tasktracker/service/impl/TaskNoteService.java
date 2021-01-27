package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.repository.TaskRepository;
import ru.progwards.tasktracker.repository.TaskNoteRepository;
import ru.progwards.tasktracker.service.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Бизнес-логика модели комментариев к задаче
 *
 * @author Konstantin Kishkin
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskNoteService implements CreateService<TaskNote>, GetService<Long, TaskNote>,
        RemoveService<TaskNote>, RefreshService<TaskNote>, GetListService<TaskNote>, TemplateService<TaskNote> {

    private final @NonNull TaskNoteRepository taskNoteRepository;

    @Override
    public void create(TaskNote model) {
        taskNoteRepository.save(model);
    }

    @Override
    public List<TaskNote> getList() {
        return taskNoteRepository.findAll();
    }

    @Override
    public TaskNote get(Long id) {
        return taskNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TaskNote id=" + id + " not found"));
    }

    @Override
    public void refresh(TaskNote model) {
        User updater = model.getUpdater();
        String comment = model.getComment();
        TaskNote copyTaskNote = new TaskNote(model.getId(), model.getTask(), model.getAuthor(), updater, comment, model.getCreated(), ZonedDateTime.now());
        taskNoteRepository.save(copyTaskNote);
    }

    @Override
    public void remove(TaskNote model) {
        taskNoteRepository.delete(model);
    }

    /**
     * Метод создания TaskNote по шаблону
     *
     * @param args – [0] - Task, [1] - User, [2] - Integer (количество создаваемых комментариев)
     */
    @Transactional
    @Override
    public void createFromTemplate(Object... args) {
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

            taskNoteRepository.save(taskNote);
        }
    }
}

