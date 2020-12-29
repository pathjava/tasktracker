package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.TaskNote;
import ru.progwards.tasktracker.model.User;
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
        RemoveService<TaskNote>, RefreshService<TaskNote>, GetListService<TaskNote> {

    private final @NonNull TaskNoteRepository taskNoteRepository;
    private final @NonNull TaskRepository taskRepository;

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
        if (taskRepository.existsTaskByTaskNote(model))
            throw new OperationIsNotPossibleException(
                    "Удаление невозможно, TaskNote id=" + model.getId() + " используется!"
            );
        taskNoteRepository.delete(model);
    }
}

