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

import java.util.Collection;

/**
 * Бизнес-логика работы с содержимым вложений
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class TaskAttachmentContentService implements CreateService<TaskAttachmentContent>, RemoveService<TaskAttachmentContent>, GetService<Long, TaskAttachmentContent> {

    private final TaskAttachmentContentRepository contentRepository;

    /**
     * Сохранение файла в таблицу вложений
     *
     * @param attachment вложение
     */
    @Override
    public void create(TaskAttachmentContent attachment) {
        contentRepository.save(attachment);
    }


    /**
     * Удаление файла из таблицы вложений
     *
     * @param attachment вложение
     */
    @Transactional
    @Override
    public void remove(TaskAttachmentContent attachment) {
        // проверить, имеются ли TaskAttachment, в которых TaskAttachment.content_id = TaskAttachmentContent.id
        Collection<TaskAttachment> taskAttachments = attachment.getTaskAttachment();
        if(taskAttachments.size() == 0) {
            // удалить, если связей нет
            contentRepository.delete(attachment);
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
        return contentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TaskAttachmentContent id=" + id + " not found"));
    }

}