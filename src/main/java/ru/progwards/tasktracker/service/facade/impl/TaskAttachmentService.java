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
import ru.progwards.tasktracker.service.vo.AttachmentContent;
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
    private Repository<Long, TaskAttachmentEntity> taskAttachmentRepository;
    @Autowired
    private RepositoryByTaskId<Long, TaskAttachmentEntity> taskAttachmentEntityRepositoryByTaskId;
    @Autowired
    private Converter<TaskAttachmentEntity, TaskAttachment> taskAttachmentConverter;
    @Autowired
    private CreateService<AttachmentContent> attachmentContentCreateService;


    /**
     * Прикрепление файла к задаче
     *
     * Если подкреплен AttachmentContent, а getAttachmentContentId не задан, то создаем и AttachmentContent
     *
     * @param taskAttachment описание связки
     */
    @Override
    public void create(TaskAttachment taskAttachment) {
        Long contentId = taskAttachment.getContentId();
        AttachmentContent content = taskAttachment.getContent();
        if(contentId==null && content != null) {
            // Если содержимое новое, добавим его в таблицу содержимого
            if (content.getId() == null || content.getId() <= 0) {
                attachmentContentCreateService.create(content);
            }
            taskAttachment.setContentId(content.getId());
        }
        TaskAttachmentEntity entity = taskAttachmentConverter.toEntity(taskAttachment);
        taskAttachmentRepository.create(entity);
        taskAttachment.setId(entity.getId());
    }


    /**
     * Отвязываем файл от задачи
     *
     * @param taskAttachment описание связки
     */
    @Override
    public void remove(TaskAttachment taskAttachment) {
        taskAttachmentRepository.delete(taskAttachment.getId());
    }


    /**
     * Получить информацию по связке задача-вложение
     *
     * @param id идентификатор связки
     * @return связка вложения с задачей
     */
    @Override
    public TaskAttachment get(Long id) {
        return taskAttachmentConverter.toVo(taskAttachmentRepository.get(id));
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
        Collection<TaskAttachmentEntity> taskAttachmentEntities = taskAttachmentEntityRepositoryByTaskId.getByTaskId(taskId);
        List<TaskAttachment> taskAttachments = new ArrayList<>(taskAttachmentEntities.size());
        // преобразуем к бизнес-объектам
        for (TaskAttachmentEntity entity:taskAttachmentEntities) {
            taskAttachments.add(taskAttachmentConverter.toVo(entity));
        }
        return taskAttachments;
    }

}
