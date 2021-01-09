package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.TaskAttachment;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.repository.TaskAttachmentRepository;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;

import java.time.ZonedDateTime;

/**
 * Бизнес-логика работы со связкой задачи и вложения
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class TaskAttachmentService implements CreateService<TaskAttachment>, RemoveService<TaskAttachment>, GetService<Long, TaskAttachment> {

    private final TaskAttachmentRepository repository;
    private final CreateService<TaskAttachmentContent> contentCreateService;
    private final RemoveService<TaskAttachmentContent> contentRemoveService;


    /**
     * Прикрепление файла к задаче
     *
     * Если подкреплен AttachmentContent, а getAttachmentContentId не задан, то создаем и AttachmentContent
     *
     * @param taskAttachment описание связки
     */
    @Override
    public void create(TaskAttachment taskAttachment) {
        // сохраним содержимое, если подкреплено
        TaskAttachmentContent content = taskAttachment.getContent();
        if(content != null) {
            // Если содержимое новое, добавим его в таблицу содержимого
            if (content.getId() == null || content.getId() <= 0) {
                contentCreateService.create(content);
            }
        }
        // установим время создания
        taskAttachment.setCreated(ZonedDateTime.now());
        // сохраним в репозиторий
        repository.save(taskAttachment);
    }


    /**
     * Отвязываем файл от задачи
     *
     * @param taskAttachment описание связки
     */
    @Transactional
    @Override
    public void remove(TaskAttachment taskAttachment) {
        TaskAttachmentContent content = taskAttachment.getContent();
        repository.delete(taskAttachment);
        // содержимое удаляем ПОСЛЕ удаления TaskAttachment
        contentRemoveService.remove(content);
    }


    /**
     * Получить информацию по связке задача-вложение
     *
     * @param id идентификатор связки
     * @return связка вложения с задачей
     */
    @Override
    public TaskAttachment get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("TaskAttachment id=" + id + " not found"));
    }

}
