package ru.progwards.tasktracker.service.template;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.repository.RelatedTaskRepository;
import ru.progwards.tasktracker.repository.TaskRepository;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.service.impl.RelatedTaskService;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Бизнес-логика создания задачи (Task)
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskTemplateService implements TemplateService<Task> {

    CreateService<Task> taskCreateService;
    GetListService<TaskPriority> taskPriorityGetListService;
    /**
     * Метод создания Task по шаблону
     *
     * @param args – [0] - Project, [1] - User
     */
    @Override
    public List<Task> createFromTemplate(Object... args) {
        if (args.length != 2)
            throw new OperationIsNotPossibleException("Task.createFromTemplate: 2 arguments expected");
        if (!(args[0] instanceof Project))
            throw new OperationIsNotPossibleException("Task.createFromTemplate: argument 0 must be Project");
        if (!(args[1] instanceof User))
            throw new OperationIsNotPossibleException("Task.createFromTemplate: argument 1 must be User");
        Project project = (Project) args[0];
        User user = (User) args[1];

        String[][] names = { // массив входных параметров: имя, описание
                {"Это ваша первая задача", "Это ваша первая задача.\n" +
                        "Проблемы - это то, что вы делаете в проекте. В бизнес-проектах проблемы называются задачами.\n" +
                        "\n" +
                        "Типы задач\n" +
                        "Задача может представлять собой документ, творческий актив, покупку и даже человека.\n"},
                {"Рабочие процессы и статусы", "Рабочие процессы\n" +
                        "Рабочие процессы определяют шаги для выполнения задачи. Чтобы увидеть рабочий процесс, через который проходит эта задача, нажмите «Просмотреть рабочий процесс» выше.\n" +
                        "\n" +
                        "Статус\n" +
                        "Статус представляет собой «состояние» задачи в определенной точке рабочего процесса. Текущий статус вашей задачи можно посмотреть в разделе «Подробности» выше. Когда вы будете готовы перейти к следующему шагу, нажмите соответствующую кнопку перехода."},
                {"Редактирование задач","Редактирование задач\n" +
                        "Наведите курсор на контент, который хотите отредактировать, и внесите изменения. Нажмите на галочку, и все готово! Вы также можете редактировать, используя сочетания клавиш или нажав кнопку «Изменить». И не забудьте поручить кому-нибудь задачу.\n" +
                        "\n" +
                        "Комментирование\n" +
                        "Вы можете добавить комментарии к задаче ниже. Комментарии - отличный способ общаться с вашей командой и оставаться в курсе. Кроме того, вы можете уведомить определенных членов команды, используя @mentions."},
                {"Поиск информации","Поиск информации\n" +
                        "Используйте панель поиска в правом верхнем углу, чтобы быстро найти конкретную задачу.\n" +
                        "Для более расширенного поиска нажмите «Искать проблемы» в меню «Проблемы»."}
        };
        Random random = new Random();
        List<TaskType> taskTypes = project.getTaskTypes();
        List<TaskPriority> priorities = taskPriorityGetListService.getList();
        List<Task> result = new ArrayList<>(names.length);
        for (String[] name: names) {
            int randomTaskType = random.nextInt(taskTypes.size());
            int randomPriority = random.nextInt(priorities.size());
            Task task = new Task();
            task.setName(name[0]);
            task.setDescription(name[1]);
            task.setType(taskTypes.get(randomTaskType));
            task.setPriority(priorities.get(randomPriority));
            task.setProject(project);
            task.setAuthor(user);
            taskCreateService.create(task);
            result.add(task);
        }
        return result;
    }
}
