package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.TaskAttachmentEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.TaskAttachmentContent;
import ru.progwards.tasktracker.service.vo.TaskAttachment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Бизнес-логика работы со связкой задачи и вложения
 *
 * @author Gregory Lobkov
 */
@Service
public class TaskAttachmentService implements CreateService<TaskAttachment>, RemoveService<TaskAttachment>, GetService<Long, TaskAttachment>, GetListByTaskService<Long, TaskAttachment> {

    @Autowired
    private Repository<Long, TaskAttachmentEntity> repository;
    @Autowired
    private RepositoryByTaskId<Long, TaskAttachmentEntity> repositoryByTaskId;
    @Autowired
    private Converter<TaskAttachmentEntity, TaskAttachment> converter;
    @Autowired
    private CreateService<TaskAttachmentContent> attachmentContentCreateService;
    @Autowired
    private RemoveService<TaskAttachmentContent> attachmentContentRemoveService;


    /**
     * Прикрепление файла к задаче
     *
     * Если подкреплен AttachmentContent, а getAttachmentContentId не задан, то создаем и AttachmentContent
     *
     * @param taskAttachment описание связки
     */
    @Override
    public void create(TaskAttachment taskAttachment) {
//        // сохраним содержимое, если подкреплено
//        Long contentId = taskAttachment.getContentId();
//        AttachmentContent content = taskAttachment.getContent();
//        if(contentId==null && content != null) {
//            // Если содержимое новое, добавим его в таблицу содержимого
//            if (content.getId() == null || content.getId() <= 0) {
//                attachmentContentCreateService.create(content);
//            }
//            taskAttachment.setContentId(content.getId());
//        }
//        // установим время создания
//        taskAttachment.setCreated(ZonedDateTime.now());
//        // сохраним в репозиторий
//        TaskAttachmentEntity entity = converter.toEntity(taskAttachment);
//        repository.create(entity);
//        taskAttachment.setId(entity.getId());
    }


    /**
     * Отвязываем файл от задачи
     *
     * @param taskAttachment описание связки
     */
    @Override
    public void remove(TaskAttachment taskAttachment) {
        TaskAttachmentContent content = taskAttachment.getContent();
        repository.delete(taskAttachment.getId());
        // содержимое удаляем ПОСЛЕ удаления TaskAttachment
        attachmentContentRemoveService.remove(content);
    }


    /**
     * Получить информацию по связке задача-вложение
     *
     * @param id идентификатор связки
     * @return связка вложения с задачей
     */
    @Override
    public TaskAttachment get(Long id) {
        return converter.toVo(repository.get(id));
    }


    /**
     * Получить список всех вложений по задаче
     *
     * @param taskId идентификатор задачи
     * @return список вложений
     */
    @Override
    public Collection<TaskAttachment> getListByTaskId(Long taskId) {
        // получили список сущностей
        Collection<TaskAttachmentEntity> taskAttachmentEntities = repositoryByTaskId.getByTaskId(taskId);
        List<TaskAttachment> taskAttachments = new ArrayList<>(taskAttachmentEntities.size());
        // преобразуем к бизнес-объектам
        for (TaskAttachmentEntity entity:taskAttachmentEntities) {
            taskAttachments.add(converter.toVo(entity));
        }
        return taskAttachments;
    }

}
