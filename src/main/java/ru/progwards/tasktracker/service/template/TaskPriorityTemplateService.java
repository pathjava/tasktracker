package ru.progwards.tasktracker.service.template;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.TaskPriority;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.TemplateService;

import java.util.Collections;
import java.util.List;


/**
 * Бизнес-логика шаблона TaskPriority
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskPriorityTemplateService implements TemplateService<TaskPriority> {

    CreateService<TaskPriority> taskPriorityCreateService;

    /**
     * Метод создания RelationType по шаблону
     *
     * @param args null - метод без параметров
     */
    @Override
    public List<TaskPriority> createFromTemplate(Object... args) {
        if (args.length != 0)
            throw new OperationIsNotPossibleException("TaskPriority.createFromTemplate: No arguments expected");

        TaskPriority p1 = new TaskPriority(null, "Самый высокий", 1, Collections.emptyList());
        TaskPriority p2 = new TaskPriority(null, "Высокий", 2, Collections.emptyList());
        TaskPriority p3 = new TaskPriority(null, "Средний", 3, Collections.emptyList());
        TaskPriority p4 = new TaskPriority(null, "Низкий", 4, Collections.emptyList());
        TaskPriority p5 = new TaskPriority(null, "Самый низкий", 5, Collections.emptyList());

        taskPriorityCreateService.create(p1);
        taskPriorityCreateService.create(p2);
        taskPriorityCreateService.create(p3);
        taskPriorityCreateService.create(p4);
        taskPriorityCreateService.create(p5);

        return List.of(p1, p2, p3, p4, p5);
    }

}