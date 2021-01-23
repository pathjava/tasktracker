package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskAttachment;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.repository.TaskAttachmentRepository;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;
import ru.progwards.tasktracker.service.TemplateService;

import java.time.ZonedDateTime;
import java.util.Collections;

/**
 * Бизнес-логика работы со связкой задачи и вложения
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class TaskAttachmentService implements CreateService<TaskAttachment>, RemoveService<TaskAttachment>, GetService<Long, TaskAttachment>, TemplateService<TaskAttachment> {

    private final TaskAttachmentRepository repository;
    private final CreateService<TaskAttachmentContent> createService;
    private final RemoveService<TaskAttachmentContent> removeService;


    /**
     * Прикрепление файла к задаче
     *
     * Если подкреплен AttachmentContent, а getAttachmentContentId не задан, то создаем и AttachmentContent
     *
     * @param attachment описание связки
     */
    @Override
    public void create(TaskAttachment attachment) {
        // сохраним содержимое, если подкреплено
        TaskAttachmentContent content = attachment.getContent();
        if(content != null) {
            // Если содержимое новое, добавим его в таблицу содержимого
            if (content.getId() == null || content.getId() <= 0) {
                createService.create(content);
            }
        }
        // установим время создания
        attachment.setCreated(ZonedDateTime.now());
        // сохраним в репозиторий
        repository.save(attachment);
    }


    /**
     * Отвязываем файл от задачи
     *
     * @param attachment описание связки
     */
    @Transactional
    @Override
    public void remove(TaskAttachment attachment) {
        TaskAttachmentContent content = attachment.getContent();
        repository.delete(attachment);
        // содержимое удаляем ПОСЛЕ удаления TaskAttachment
        removeService.remove(content);
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

    /**
     * Создать бизнес-объект по шаблону
     *
     * @param args [0] - Task
     */
    @Transactional
    @Override
    public void createFromTemplate(Object... args) {
        if (args.length != 1)
            throw new OperationIsNotPossibleException(
                    "TaskAttachment.createFromTemplate: 1 argument expected");
        if (!(args[0] instanceof Task))
            throw new OperationIsNotPossibleException(
                    "TaskAttachment.createFromTemplate: argument 0 must be Task");

        Task task = (Task)args[0];

        byte[] data = ("\nHello,\nthis is a sample of file,\n attached to the Task \""+task.getName()+"\".\n").getBytes();
        TaskAttachmentContent content = new TaskAttachmentContent(null, data, Collections.emptyList());
        TaskAttachment attachment = new TaskAttachment(null, task, "Sample attachment", "txt", (long)data.length, null, content);

        create(attachment);
    }

}
