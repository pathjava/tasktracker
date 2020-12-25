package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.repository.TaskRepository;
import ru.progwards.tasktracker.service.GetService;

/**
 * Бизнес-логика получения задачи (Task) по коду
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskByCodeGetService implements GetService<String, Task> {

    private final @NonNull TaskRepository taskRepository;

    /**
     * Метод получения задачи (Task) по текстовому коду задачи
     *
     * @param code строковое значение кода задачи, создаваемое на основе префикса проекта задачи
     * @return найденную задачу или пусто
     */
    @Override
    public Task get(String code) {
        return taskRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new NotFoundException("Task code=" + code + " not found"));
    }
}
