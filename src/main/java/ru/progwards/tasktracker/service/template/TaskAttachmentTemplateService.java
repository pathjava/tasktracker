package ru.progwards.tasktracker.service.template;

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
import ru.progwards.tasktracker.service.*;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Бизнес-логика создания шаблона
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class TaskAttachmentTemplateService implements TemplateService<TaskAttachment> {

    CreateService<TaskAttachment> taskAttachmentCreateService;

    /**
     * Создать бизнес-объект по шаблону
     *
     * @param args [0] - Task
     */
    @Override
    public List<TaskAttachment> createFromTemplate(Object... args) {
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

        taskAttachmentCreateService.create(attachment);
        return List.of(attachment);
    }
}
