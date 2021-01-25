package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.TaskAttachment;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.repository.TaskAttachmentContentRepository;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;

import java.util.List;

/**
 * Бизнес-логика работы с содержимым вложений
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class TaskAttachmentContentService implements CreateService<TaskAttachmentContent>, RemoveService<TaskAttachmentContent>, GetService<Long, TaskAttachmentContent> {

    private final TaskAttachmentContentRepository repository;

    /**
     * Сохранение файла в таблицу вложений
     *
     * @param content вложение
     */
    @Transactional
    @Override
    public void create(TaskAttachmentContent content) {
        repository.save(content);
    }


    /**
     * Удаление файла из таблицы вложений
     *
     * @param content вложение
     */
    @Transactional
    @Override
    public void remove(TaskAttachmentContent content) {
        // проверить, имеются ли TaskAttachment, в которых TaskAttachment.content_id = TaskAttachmentContent.id
        List<TaskAttachment> taskAttachments = content.getTaskAttachment();
        if(taskAttachments.size() == 0) {
            // удалить, если связей нет
            repository.delete(content);
        }
    }


    /**
     * Получить файл-вложение для передачи пользователю
     *
     * @param id идентификатор
     * @return описание файла-вложения вместе с содержимым
     */
    @Override
    public TaskAttachmentContent get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("TaskAttachmentContent id=" + id + " not found"));
    }

}